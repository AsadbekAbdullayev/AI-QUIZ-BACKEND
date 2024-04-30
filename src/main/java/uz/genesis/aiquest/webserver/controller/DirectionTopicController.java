package uz.genesis.aiquest.webserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.genesis.aiquest.webserver.models.dto.DirectionTopicDTO;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.service.DirectionTopicService;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/ai/topics")
@RequiredArgsConstructor
@SecurityRequirement(name = "talent")
public class DirectionTopicController {
    private final DirectionTopicService directionTopicService;

    @GetMapping
    public Header<List<DirectionTopicDTO>> getList(@RequestParam Long subdirectionId) throws JsonProcessingException {
        return Header.ok(directionTopicService.getAllTopics(subdirectionId));
    }
}
