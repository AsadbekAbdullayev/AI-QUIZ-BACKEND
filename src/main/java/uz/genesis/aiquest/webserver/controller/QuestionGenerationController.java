package uz.genesis.aiquest.webserver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.webserver.models.dto.*;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.service.ConductedTestService;
import uz.genesis.aiquest.webserver.service.QuestionGenerationService;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/verification-questions")
@SecurityRequirement(name = "talent")
public class QuestionGenerationController {

    private final QuestionGenerationService questionGenerationService;
    private final ConductedTestService conductedTestService;

    @GetMapping(value = "/generate/{subdirectionId}")
    public Header<ConductedTestCreateDTO> generateProfileVerificationTests(@AuthenticationPrincipal UserTalent userTalent, @PathVariable Long subdirectionId) {
        return Header.ok(questionGenerationService.generateQuestionsForStandardTest(userTalent, subdirectionId));
    }

    @GetMapping(value = "/generate/detailed/{subdirectionId}")
    public Header<ConductedTestCreateDTO> generateDetailedVerificationTests(@AuthenticationPrincipal UserTalent userTalent, @PathVariable Long subdirectionId) {
        return Header.ok(questionGenerationService.generateQuestionsForDetailed(userTalent, subdirectionId));
    }

    @GetMapping(value = "/get-one-test/{conductedTestId}")
    public Header<OneTestDTO> getQuestion(@RequestParam Integer index, @PathVariable UUID conductedTestId) {
        return Header.ok(questionGenerationService.getTestByIndexAndConductedTestId(index, conductedTestId));
    }

    @GetMapping(value = "/get-current/{subdirectionId}/{testType}")
    public Header<ConductedTestCreateDTO> getCurrentTest(@AuthenticationPrincipal UserTalent userTalent, @PathVariable TestType testType, @PathVariable Long subdirectionId) {
        return Header.ok(questionGenerationService.getCurrentTest(userTalent, testType, subdirectionId));
    }

    @PostMapping(value = "/answer")
    public Header<OneTestDTO> answer(@RequestBody Header<AnswerTheQuestionDTO> answerTheQuestionDTOHeader) {
        return Header.ok(questionGenerationService.answerTheQuestion(answerTheQuestionDTOHeader.getData()));
    }

    @GetMapping(value = "/generate/detailed/selectable-sub-directions")
    public Header<List<DirectionDTO>> getSelectableSubDirections(@AuthenticationPrincipal UserTalent userTalent) {
        return Header.ok(conductedTestService.getDirectionsOfPassedStandardTestAssessment(userTalent.getId()));
    }
}
