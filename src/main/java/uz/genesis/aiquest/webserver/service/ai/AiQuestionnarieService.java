package uz.genesis.aiquest.webserver.service.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.AiQuestionnarieDTO;
import uz.genesis.aiquest.webserver.models.entity.AiQuestion;
import uz.genesis.aiquest.webserver.models.entity.ConductedTest;
import uz.genesis.aiquest.webserver.models.entity.DirectionTopic;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.repository.AiQuestionRepository;
import uz.genesis.aiquest.webserver.repository.DirectionTopicRepository;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;
import uz.genesis.aiquest.webserver.utils.PromptUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiQuestionnarieService {

    private final AiQuestionRepository aiQuestionRepository;
    private final OpenAiChatClient openAiChatClient;
    private final ObjectMapper objectMapper;
    private final DirectionTopicRepository directionTopicRepository;
    private final SubDirectionRepository subDirectionRepository;

    public AiQuestion addUserTextedAnswer(AiQuestionnarieDTO aiQuestionnarieDTO) {
        AiQuestion aiQuestionnarie = aiQuestionRepository.findById(aiQuestionnarieDTO.getId())
                .orElseThrow(GeneralApiException.throwEx("resource_not_found", ErrorCodes.ERR_NOT_FOUND));
        aiQuestionnarie.setUserAnswer(aiQuestionnarieDTO.getUserAnswer());
        return aiQuestionRepository.save(aiQuestionnarie);
    }

    public AiQuestion generateOneQuestion(ConductedTest conductedTest, Integer index) throws JsonProcessingException {
        AiQuestion aiQuestionnarie = aiQuestionRepository.findByQuestionIndexAndConductedTestId(index, conductedTest.getId())
                .orElseThrow(GeneralApiException.throwEx("resource_not_found", ErrorMessages.NOT_FOUND));
        if (Objects.nonNull(aiQuestionnarie.getAiQuestion())) {
            //already generated
            return aiQuestionnarie;
        }
        Prompt createAiQuestionPrompt = PromptUtils.getCreateAiQuestionPrompt(aiQuestionnarie.getDirectionTopic());
        ChatResponse chatResponse = openAiChatClient.call(createAiQuestionPrompt);
        String content = chatResponse.getResult().getOutput().getContent();
        JsonNode jsonNode = objectMapper.readTree(content);
        String question = jsonNode.get("question").asText();
        aiQuestionnarie.setAiQuestion(question);
        aiQuestionnarie.setConductedTest(conductedTest);
        return aiQuestionRepository.save(aiQuestionnarie);
    }

    public List<AiQuestion> generateTemplateRows(UserTalent userTalent, Long subDirectionId, ConductedTest conductedTest) {
        var subDirection = subDirectionRepository.findById(subDirectionId).orElseThrow();
        var directionTest = subDirection.getDirectionTest();
        if (Objects.isNull(directionTest))
            throw new GeneralApiException("currently_we_cannot_provide_test_to_this_direction", ErrorCodes.ERROR);

        //detailed test niki bilan bir xil bo'ladi vaqt va soni
        Integer detailedTestQuantity = directionTest.getDetailedTestQuantity();

        List<AiQuestion> questionnaries = new ArrayList<>();
        for (int i = 0; i < detailedTestQuantity; i++) {
            DirectionTopic randomDirectionTopic = directionTopicRepository.getRandomDirectionTopic(subDirectionId);
            if (randomDirectionTopic == null) {
                throw new GeneralApiException("no_direction_topic_found", ErrorCodes.ERROR);
            }
            AiQuestion aiQuestionnarie =
                    new AiQuestion(
                            null,
                            null,
                            userTalent,
                            i,
                            conductedTest,
                            randomDirectionTopic
                    );
            questionnaries.add(aiQuestionnarie);
        }
        return aiQuestionRepository.saveAll(questionnaries);
    }

    public List<AiQuestion> getAiQuestionsAndAnswers(UUID conductedTestId, UserTalent userTalent) {
       return aiQuestionRepository.findAllByConductedTestIdAndUserTalentId(conductedTestId, userTalent.getId());
    }
}
