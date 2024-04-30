package uz.genesis.aiquest.webserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.service.DirectionTestService;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.entity.*;
import uz.genesis.aiquest.webserver.repository.GeneratedTestRepository;
import uz.genesis.aiquest.webserver.repository.QuestionRepository;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneratedTestService {

    private final QuestionRepository questionRepository;
    private final DirectionTestService directionTestService;

    private final GeneratedTestRepository generatedTestRepository;
    private final SubDirectionRepository subDirectionRepository;


    public List<GeneratedTest> generateTest(UserTalent userTalent, Long subDirectionId, ConductedTest conductedTest) {
        var subDirection = subDirectionRepository.findById(subDirectionId).orElseThrow();
        var directionTest = subDirection.getDirectionTest();
        if (Objects.isNull(directionTest))
            throw new GeneralApiException("currently_we_cannot_provide_test_to_this_direction", ErrorCodes.ERROR);

        if (directionTest.getTestQuantityByType(conductedTest.getTestType()) > directionTestService.getValidTestQuantityByType(conductedTest.getTestType(), subDirectionId)) {
            throw new GeneralApiException("currently_no_tests_available");
        }
        var questions = questionRepository.generateRandomQuestions(subDirectionId, directionTest.getTestQuantityByType(conductedTest.getTestType()), Utils.getLangInString(), conductedTest.getTestType());

        if (questions.size() < directionTest.getTestQuantityByType(conductedTest.getTestType())) {
            throw new GeneralApiException("currently_no_tests_available");
        }
        List<GeneratedTest> generatedTests = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            var eachQuestion = questions.get(i);
            GeneratedTest generatedTest = new GeneratedTest();
            generatedTest.setQuestion(eachQuestion);
            generatedTest.setUserTalent(userTalent);
            generatedTest.setQuestionIndex(i);
            generatedTest.setConductedTest(conductedTest);
            generatedTest.setQuestionAnswers(getQuestionAnswers(eachQuestion));
            generatedTest.getQuestionAnswers().forEach(questionAnswer -> questionAnswer.setGeneratedTest(generatedTest));
            generatedTests.add(generatedTest);
        }
        return generatedTestRepository.saveAll(generatedTests);

    }

    private static List<QuestionAnswer> getQuestionAnswers(Question eachQuestion) {
        return eachQuestion.getQuestionOptions().stream()
                .map(questionOption -> new QuestionAnswer(questionOption, false, null)).toList();
    }


    public GeneratedTest getTestByIndexAndConductedTestId(Integer index, UUID conductedTestId) {
        return generatedTestRepository.findByConductedTestIdAndQuestionIndex(conductedTestId, index)
                .orElseThrow(GeneralApiException.throwEx("index_or_test_not_found", ErrorCodes.ERR_BAD_REQUEST));
    }

    public GeneratedTest getTestById(UUID testId) {
        return generatedTestRepository.findById(testId)
                .orElseThrow(GeneralApiException.throwEx("index_or_test_not_found", ErrorCodes.ERR_BAD_REQUEST));
    }

    public GeneratedTest save(GeneratedTest generatedTest) {
        return generatedTestRepository.save(generatedTest);
    }


}
