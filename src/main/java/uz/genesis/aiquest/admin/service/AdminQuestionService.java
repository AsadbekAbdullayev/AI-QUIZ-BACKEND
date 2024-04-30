package uz.genesis.aiquest.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.models.dto.AddQuestionDTO;
import uz.genesis.aiquest.webserver.models.dto.CustomPage;
import uz.genesis.aiquest.webserver.models.dto.QuestionOptionDTO;
import uz.genesis.aiquest.webserver.models.dto.req.PaginationRequest;
import uz.genesis.aiquest.webserver.models.enums.QuestionValidationState;
import uz.genesis.aiquest.webserver.repository.QuestionRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.commons.exception.BadRequestException;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.QuestionDTO;
import uz.genesis.aiquest.webserver.models.enums.QuestionLanguage;
import uz.genesis.aiquest.webserver.models.enums.QuestionType;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.models.mappers.QuestionMapper;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;


    public List<QuestionDTO> addQuestions(AddQuestionDTO addQuestionDTO) {
        var questionDTOS = addQuestionDTO.getQuestions();
        if (!isValidRequest(questionDTOS)) {
            throw new BadRequestException("correct_options_exceeded", ErrorCodes.ERR_BAD_REQUEST);
        }

        questionDTOS.forEach(question -> {
            question.setForTestType(addQuestionDTO.getTestType());
            question.setQuestionLanguage(addQuestionDTO.getQuestionLanguage());
            question.setQuestionType(determineQuestionType(question.getQuestionOptions()));
            question.setDirectionId(addQuestionDTO.getDirectionId());
            question.setQuestionValidationState(QuestionValidationState.PENDING);
        });
        var questions = questionMapper.toEntityList(questionDTOS);
        questions.forEach(question -> question.getQuestionOptions().forEach(questionOption -> questionOption.setQuestion(question)));
        var persistedQuestions = questionRepository.saveAll(questions);
        return questionMapper.toDtoList(persistedQuestions);
    }

    public QuestionDTO getOneQuestion(UUID questionId) {
        var question = questionRepository.findById(questionId)
                .orElseThrow(GeneralApiException.throwEx("question_not_found", ErrorCodes.ERR_BAD_REQUEST));
        return questionMapper.toDto(question);
    }


    public CustomPage<QuestionDTO> getQuestionList(PaginationRequest paginationRequest, QuestionLanguage questionLanguage, Long subDirectionId, TestType testType) {
        Pageable pageRequest = paginationRequest.getPageRequest(Sort.by("questionValidationState")
                .descending());

        if (testType != null) {
            var questionPage = questionRepository.findAllByQuestionLanguageAndDirectionIdAndForTestType(questionLanguage, pageRequest, subDirectionId,testType);
            return CustomPage.of(questionPage, questionMapper.toDtoList(questionPage.getContent()));
        }
            var questionPageNotTestType = questionRepository.findAllByQuestionLanguageAndDirectionId(questionLanguage, pageRequest, subDirectionId);
            return CustomPage.of(questionPageNotTestType, questionMapper.toDtoList(questionPageNotTestType.getContent()));
    }

    private static QuestionType determineQuestionType(List<QuestionOptionDTO> questionOptions) {
        var correctAnswerCount = getCorrectAnswerCount(questionOptions);
        if (correctAnswerCount == 1)
            return QuestionType.ONE_CHOICE;
        else if (correctAnswerCount == 2)
            return QuestionType.MULTIPLE_CHOICE;
        return QuestionType.POLL;
    }

    private static boolean isValidRequest(List<QuestionDTO> questionDTOS) {
        return questionDTOS
                .stream()
                .allMatch(AdminQuestionService::isCorrectAnswersNoMoreThan2);

    }

    private static boolean isCorrectAnswersNoMoreThan2(QuestionDTO questionDTO) {
        return getCorrectAnswerCount(questionDTO.getQuestionOptions()) < 3;
    }

    private static long getCorrectAnswerCount(List<QuestionOptionDTO> questionOptions) {
        return questionOptions
                .stream()
                .filter(QuestionOptionDTO::getIsCorrect)
                .count();
    }

    public QuestionDTO editQuestion(QuestionDTO questionDTO) {
        var question = questionRepository.findById(questionDTO.getId())
                .orElseThrow(GeneralApiException.throwEx("question_not_found", ErrorCodes.ERR_BAD_REQUEST));
        questionMapper.updateEntityFromDto(question, questionDTO);
        question.getQuestionOptions().forEach(questionOption -> questionOption.setQuestion(question));
        var saved = questionRepository.save(question);
        return questionMapper.toDto(saved);
    }

    public void delete(UUID questionId) {
        try {
            questionRepository.deleteById(questionId);
        } catch (Exception e) {
            log.info("Cannot delete question");
        }
    }
}
