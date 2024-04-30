package uz.genesis.aiquest.webserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.models.dto.DirectionTopicDTO;
import uz.genesis.aiquest.webserver.models.dto.res.AiTopicResponse;
import uz.genesis.aiquest.webserver.models.entity.DirectionTopic;
import uz.genesis.aiquest.webserver.repository.DirectionTopicRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.PromptUtils;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;
import uz.genesis.aiquest.webserver.models.mappers.DirectionTopicMapper;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectionTopicService {

    private final DirectionTopicRepository directionTopicRepository;
    private final OpenAiChatClient chatClient;
    private final SubDirectionRepository subDirectionRepository;
    private final ObjectMapper objectMapper;
    private final DirectionTopicMapper directionTopicMapper;

    public List<DirectionTopicDTO> generateOrGetFromDbDirectionTopics(Long subdirectionId) throws JsonProcessingException {
        SubDirection subdirection = subDirectionRepository.findById(subdirectionId)
                .orElseThrow(GeneralApiException.throwEx("cannot_find_subdirection", ErrorCodes.ERR_NOT_FOUND));

        if (directionTopicRepository.existsBySubDirectionId(subdirectionId)) {
            List<DirectionTopic> topics = directionTopicRepository.findAllBySubDirectionId(subdirectionId);
            return directionTopicMapper.toDtoList(topics);
        }

        Prompt topicGeneratePrompt = PromptUtils.getTopicGeneratePrompt(subdirection);
        ChatResponse chatResponse = chatClient.call(topicGeneratePrompt);
        String content = chatResponse.getResult().getOutput().getContent();

        AiTopicResponse topics = objectMapper.readValue(content, AiTopicResponse.class);
        List<DirectionTopicDTO> listOfDirectionTopics = topics.getTopics().stream()
                .map(DirectionTopicDTO::new)
                .toList();

        List<DirectionTopic> directionTopicEntityList = directionTopicMapper.toEntityList(listOfDirectionTopics);
        directionTopicEntityList.forEach(directionTopic -> directionTopic.setSubDirection(subdirection));
        directionTopicRepository.saveAll(directionTopicEntityList);
        return listOfDirectionTopics;
    }

    public List<DirectionTopicDTO> getAllTopics(Long subdirectionId) throws JsonProcessingException {
        if (directionTopicRepository.existsBySubDirectionId(subdirectionId)) {
            List<DirectionTopic> allBySubDirectionId = directionTopicRepository.findAllBySubDirectionId(subdirectionId);
            return directionTopicMapper.toDtoList(allBySubDirectionId);

        } else {
            return generateOrGetFromDbDirectionTopics(subdirectionId);
        }
    }
}
