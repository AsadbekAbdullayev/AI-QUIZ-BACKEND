package uz.genesis.aiquest.webserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.*;
import uz.genesis.aiquest.webserver.models.entity.*;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.models.mappers.QuestionAnswerMapper;
import uz.genesis.aiquest.webserver.repository.GeneratedTestRepository;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;
import uz.genesis.aiquest.webserver.repository.UserTalentRepository;
import uz.genesis.aiquest.webserver.service.ai.AiQuestionnarieService;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.PromptUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionGenerationService {

    private final GeneratedTestService generatedTestService;
    private final GeneratedTestRepository generatedTestRepository;
    private final SubDirectionRepository subDirectionRepository;
    private final ConductedTestService conductedTestService;
    private final AiQuestionnarieService aiQuestionnarieService;
    private final QuestionAnswerMapper questionAnswerMapper;
    private final UserTalentRepository userTalentRepository;
    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;


    @Transactional(rollbackFor = GeneralApiException.class)
    public ConductedTestCreateDTO generateQuestionsForStandardTest(UserTalent userTalent, Long subDirectionId) {

        if (conductedTestService.isTestAlreadyInProgressByUserIdAndSubdirectionIdAndTestType(userTalent.getId(), TestType.STANDARD_TEST, subDirectionId)) {
            var conductedTest = conductedTestService.getGeneratedTestByUserIdAndTestTypeAndSubdirectionId(userTalent, TestType.STANDARD_TEST, subDirectionId);
            var generatedTests = conductedTest.getGeneratedTests();

            return ConductedTestCreateDTO
                    .builder()
                    .questionIndexes(getIndexesForGeneratedTests(generatedTests))
                    .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                    .conductedTestId(conductedTest.getId())
                    .build();
        }

        var allowedToAttendWithDirection = conductedTestService.isUserAllowedToAttendWithDirection(userTalent.getId(), TestType.STANDARD_TEST, subDirectionId);

        //agar 1 hafta otib yechishga ruhsat bo'lmasa
        if (!allowedToAttendWithDirection) {
            throw new GeneralApiException("user_already_attended_to_test", ErrorCodes.ERR_TEST_ALREADY_ATTENDED);
        }

        var subDirection = subDirectionRepository.findById(subDirectionId).orElseThrow();
        var conductedTest = conductedTestService.saveConductedTest(userTalent, TestType.STANDARD_TEST, subDirection);
        var generatedTests = generatedTestService.generateTest(userTalent, subDirectionId, conductedTest);

        List<Integer> questionIndexes = getIndexesForGeneratedTests(generatedTests);

        return ConductedTestCreateDTO.builder()
                .conductedTestId(conductedTest.getId())
                .questionIndexes(questionIndexes)
                .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                .build();

    }



    @Transactional
    public ConductedTestCreateDTO generateQuestionsForDetailed(UserTalent userTalent, Long subDirectionId) {
        if (!userTalent.getIsStandardTestPassed()) {
            throw new GeneralApiException("user_did_not_passed_standard_test");
        }

        if (conductedTestService.isTestAlreadyInProgressByUserIdAndSubdirectionIdAndTestType(userTalent.getId(), TestType.DETAILED_TEST, subDirectionId)) {

            var conductedTest = conductedTestService.getGeneratedTestByUserIdAndTestTypeAndSubdirectionId(userTalent, TestType.DETAILED_TEST, subDirectionId);
            var generatedTests = conductedTest.getGeneratedTests();

            return ConductedTestCreateDTO
                    .builder()
                    .questionIndexes(getIndexesForGeneratedTests(generatedTests))
                    .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                    .conductedTestId(conductedTest.getId())
                    .build();
        }

        var allowedToAttendWithDirection = conductedTestService.isUserAllowedToAttendWithDirection(userTalent.getId(), TestType.DETAILED_TEST, subDirectionId);

        if (!allowedToAttendWithDirection) {
            throw new GeneralApiException("user_already_attended_to_test", ErrorCodes.ERR_TEST_ALREADY_ATTENDED);
        }

        var subDirection = subDirectionRepository.findById(subDirectionId).orElseThrow();
        var conductedTest = conductedTestService.saveConductedTest(userTalent, TestType.DETAILED_TEST, subDirection);
        var generatedTests = generatedTestService.generateTest(userTalent, subDirectionId, conductedTest);

        List<Integer> questionIndexes = getIndexesForGeneratedTests(generatedTests);

        return ConductedTestCreateDTO.builder()
                .conductedTestId(conductedTest.getId())
                .questionIndexes(questionIndexes)
                .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                .build();

    }

    //-------------------------AI-------------------------------------

    //ready
    @Transactional
    public ConductedTestCreateDTO generateQuestionsForAiQuestionnarie(UserTalent userTalent, Long subDirectionId) {
        if (conductedTestService.isTestAlreadyInProgressByUserIdAndSubdirectionIdAndTestType(userTalent.getId(), TestType.AI_QUESTIONARIO, subDirectionId)) {

            var conductedTest = conductedTestService.getGeneratedTestByUserIdAndTestTypeAndSubdirectionId(userTalent, TestType.AI_QUESTIONARIO, subDirectionId);
            var generatedTests = conductedTest.getGeneratedTests();

            return ConductedTestCreateDTO
                    .builder()
                    .questionIndexes(getIndexesForGeneratedTests(generatedTests))
                    .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                    .conductedTestId(conductedTest.getId())
                    .build();
        }

        var allowedToAttendWithDirection = conductedTestService.isUserAllowedToAttendWithDirection(userTalent.getId(), TestType.AI_QUESTIONARIO, subDirectionId);

        if (!allowedToAttendWithDirection) {
            throw new GeneralApiException("user_already_attended_to_test", ErrorCodes.ERR_TEST_ALREADY_ATTENDED);
        }

        var subDirection = subDirectionRepository.findById(subDirectionId).orElseThrow();
        var conductedTest = conductedTestService.saveConductedTest(userTalent, TestType.AI_QUESTIONARIO, subDirection);
        var generatedTests = aiQuestionnarieService.generateTemplateRows(userTalent, subDirectionId, conductedTest);

        List<Integer> questionIndexes = getIndexesForAiQuestionnarie(generatedTests);

        return ConductedTestCreateDTO.builder()
                .conductedTestId(conductedTest.getId())
                .questionIndexes(questionIndexes)
                .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                .build();

    }

    //ready
    public AiQuestionnarieDTO getGeneratedQuestionOfAi(Integer index, UUID conductedTestId) throws JsonProcessingException {
        ConductedTest conductedTest = conductedTestService.getById(conductedTestId);
        AiQuestion aiQuestionario = aiQuestionnarieService.generateOneQuestion(conductedTest, index);
        return AiQuestionnarieDTO.builder()
                .id(aiQuestionario.getId())
                .aiQuestion(aiQuestionario.getAiQuestion())
                .build();
    }


    public AiQuestionnarieDTO giveAnswerToAiQuestion(AiQuestionnarieDTO aiQuestionnarieDTO) {
        AiQuestion aiQuestionnarie = aiQuestionnarieService.addUserTextedAnswer(aiQuestionnarieDTO);
        return AiQuestionnarieDTO.builder()
                .id(aiQuestionnarie.getId())
                .aiQuestion(aiQuestionnarie.getAiQuestion())
                .build();
    }

    //-------------------------AI-------------------------------------

    private static List<Integer> getIndexesForGeneratedTests(List<GeneratedTest> generatedTests) {
        return generatedTests
                .stream()
                .map(GeneratedTest::getQuestionIndex)
                .sorted()
                .toList();
    }
    private static List<Integer> getIndexesForAiQuestionnarie(List<AiQuestion> generatedTests) {
        return generatedTests
                .stream()
                .map(AiQuestion::getQuestionIndex)
                .sorted()
                .toList();
    }


    public OneTestDTO getTestByIndexAndConductedTestId(Integer index, UUID conductedTestId) {
        var generatedTest = generatedTestService.getTestByIndexAndConductedTestId(index, conductedTestId);

        var question = generatedTest.getQuestion();

        var remainedTime = generatedTest.getConductedTest().getRemainedTimeInMilliseconds();
        var chosenAnswers = questionAnswerMapper.toDtoList(generatedTest.getQuestionAnswers());
        Collections.shuffle(chosenAnswers);

        return OneTestDTO
                .builder()
                .testId(generatedTest.getId())
                .questionCaption(question.getQuestionCaption())
                .questionIndex(index)
                .questionType(question.getQuestionType())
                .codeExample(question.getCodeExample())
                .remainedTime(remainedTime)
                .chosenAnswers(chosenAnswers)
                .build();

    }


    public ConductedTestCreateDTO getCurrentTest(UserTalent userTalent, TestType testType, Long subdirectionId) {
        var conductedTest = conductedTestService.getGeneratedTestByUserIdAndTestTypeAndSubdirectionId(userTalent, testType, subdirectionId);
        return ConductedTestCreateDTO
                .builder()
                .conductedTestId(conductedTest.getId())
                .remainedTime(conductedTest.getRemainedTimeInMilliseconds())
                .questionIndexes(getIndexesForGeneratedTests(conductedTest.getGeneratedTests()))
                .build();
    }



    public OneTestDTO answerTheQuestion(AnswerTheQuestionDTO answerTheQuestionDTO) {
        var chosenAnswerCount = answerTheQuestionDTO.getSelectedAnswersCount();
        if (chosenAnswerCount > 2) {
            throw new GeneralApiException("max_limit_chosen_answers_exceeded");
        }
        var generatedTest = generatedTestService.getTestById(answerTheQuestionDTO.getTestId());

        generatedTest.setAnswered(chosenAnswerCount > 0);

        if (generatedTest.getConductedTest().getRemainedTimeInMilliseconds() <= 0) {
            throw new GeneralApiException("time_is_up", ErrorCodes.THE_TIME_IS_UP);
        }
        for (QuestionAnswer questionAnswer : generatedTest.getQuestionAnswers()) {
            for (QuestionAnswerDTO questionAnswerDTO : answerTheQuestionDTO.getQuestionAnswers()) {
                if (questionAnswer.getId().equals(questionAnswerDTO.getId())) {
                    questionAnswerMapper.updateEntityFromDto(questionAnswer, questionAnswerDTO);
                }
            }
        }
        generatedTest.setIsCorrectlyAnswered(isCorrect(generatedTest));

        var savedGeneratedTest = generatedTestService.save(generatedTest);
        return OneTestDTO
                .builder()
                .chosenAnswers(questionAnswerMapper.toDtoList(savedGeneratedTest.getQuestionAnswers()))
                .testId(savedGeneratedTest.getId())
                .questionCaption(savedGeneratedTest.getQuestion().getQuestionCaption())
                .remainedTime(savedGeneratedTest.getConductedTest().getRemainedTimeInMilliseconds())
                .questionIndex(savedGeneratedTest.getQuestionIndex())
                .build();
    }


    @Transactional
    public TestResultDTO finishAndGenerateResults(UserTalent userTalent, UUID conductedTestId) {
        long startTime = System.currentTimeMillis();
        var conductedTest = conductedTestService.getById(conductedTestId);
        var generatedTests = generatedTestRepository.findByConductedTestId(conductedTestId);
        var totalGivenTests = generatedTests.size();

        var totalCorrectAnswers = (int) generatedTests
                .stream()
                .filter(this::isCorrect)
                .count();

        double nisbat = (double) totalCorrectAnswers / (double) totalGivenTests;

        var finalTestScore = (int) (nisbat * 100);

        var isAccepted = finalTestScore >= 60;
        conductedTest.setIsVerified((isAccepted));
        conductedTest.setSubmittedTime(LocalDateTime.now());
        conductedTest.setFinalScore(finalTestScore);

        userTalentRepository.save(userTalent);

        return TestResultDTO
                .builder()
                .testType(conductedTest.getTestType())
                .correctAnswers(totalCorrectAnswers)
                .totalQuestions(totalGivenTests)
                .submittedTime(LocalDateTime.now())
                .isVerified(conductedTest.getIsVerified())
                .build();

    }

    private boolean isCorrect(GeneratedTest generatedTest) {
        return isAnswersAreMatches(generatedTest
                        .getQuestion()
                        .getQuestionOptions(),
                generatedTest.getQuestionAnswers());
    }

    private boolean isAnswersAreMatches(List<QuestionOption> questionOptions, List<QuestionAnswer> questionAnswers) {
        var selectedAnswers = questionAnswers.stream()
                .filter(QuestionAnswer::getIsSelected)
                .toList();

        var correctQuestionOptions = questionOptions.stream()
                .filter(QuestionOption::getIsCorrect)
                .toList();


        if (selectedAnswers.size() != correctQuestionOptions.size())
            return false;

        for (QuestionOption correctQuestionOption : correctQuestionOptions) {
            if (selectedAnswers.stream().noneMatch(selectedAnswer -> selectedAnswer.getQuestionOption().getId().equals(correctQuestionOption.getId()))) {
                return false;
            }
        }
        return true;
    }


    public AiQuestResultDTO submitResultsToAi(UserTalent userTalent, UUID conductedTestId) throws JsonProcessingException {
        List<AiQuestion> aiQuestionsByConductedTestId =
                aiQuestionnarieService.getAiQuestionsAndAnswers(conductedTestId, userTalent);

        ConductedTest conductedTest = conductedTestService.getById(conductedTestId);
        String subDirection = conductedTest.getSubDirection().getCaption();
        Prompt prompt = PromptUtils.submitQuestionsAndAnswersPrompt(aiQuestionsByConductedTestId, subDirection, objectMapper);
        ChatResponse chatResponse = chatClient.call(prompt);
        String content = chatResponse.getResult().getOutput().getContent();
        JsonNode jsonNode = objectMapper.readTree(content);
        String approximatePercentage = jsonNode.get(PromptUtils.KeyConstants.approximate_percentage).asText();
        String conclusion = jsonNode.get(PromptUtils.KeyConstants.conclusion).asText();
        String recommendations = jsonNode.get(PromptUtils.KeyConstants.recommendations).asText();
        String seniority_level = jsonNode.get(PromptUtils.KeyConstants.seniority_level).asText();
        DirectionTest directionTest = conductedTest.getSubDirection().getDirectionTest();
        return new AiQuestResultDTO(TestType.AI_QUESTIONARIO,
                recommendations,
                seniority_level,
                conclusion,
                Integer.parseInt(approximatePercentage),
                directionTest.getTestQuantityByType(TestType.AI_QUESTIONARIO),
                LocalDateTime.now());


    }
}
