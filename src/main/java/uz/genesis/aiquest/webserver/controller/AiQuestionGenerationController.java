package uz.genesis.aiquest.webserver.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.webserver.models.dto.AiQuestResultDTO;
import uz.genesis.aiquest.webserver.models.dto.AiQuestionnarieDTO;
import uz.genesis.aiquest.webserver.models.dto.ConductedTestCreateDTO;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.service.QuestionGenerationService;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/ai-questions")
@SecurityRequirement(name = "talent")
public class AiQuestionGenerationController {

    private final QuestionGenerationService questionGenerationService;

    @GetMapping(value = "/generate/{subdirectionId}")
    public Header<ConductedTestCreateDTO> generateAiQuestions(@AuthenticationPrincipal UserTalent userTalent, @PathVariable Long subdirectionId) {
        return Header.ok(questionGenerationService.generateQuestionsForAiQuestionnarie(userTalent, subdirectionId));
    }

    @GetMapping(value = "/get-one-test/{conductedTestId}")
    public Header<AiQuestionnarieDTO> getQuestionOfAiQuestionnarie(@RequestParam Integer index, @PathVariable UUID conductedTestId) throws JsonProcessingException {
        return Header.ok(questionGenerationService.getGeneratedQuestionOfAi(index, conductedTestId));
    }

    @PostMapping(value = "/answer")
    public Header<AiQuestionnarieDTO> answerAiQuestion(@RequestBody Header<AiQuestionnarieDTO> answerOfAiQuestionnarie) {
        return Header.ok(questionGenerationService.giveAnswerToAiQuestion(answerOfAiQuestionnarie.getData()));
    }

    @GetMapping(value = "/submit/{conductedTestId}")
    public Header<AiQuestResultDTO> submit(@AuthenticationPrincipal UserTalent userTalent, @PathVariable UUID conductedTestId) throws JsonProcessingException {
        return Header.ok(questionGenerationService.submitResultsToAi(userTalent, conductedTestId));
    }


}
