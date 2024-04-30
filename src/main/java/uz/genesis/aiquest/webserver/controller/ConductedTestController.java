package uz.genesis.aiquest.webserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.service.ConductedTestService;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.UUID;

@RestController
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/conducted-tests")
@RequiredArgsConstructor
public class ConductedTestController {

    private final ConductedTestService conductedTestService;
    @GetMapping(value = "/{talentId}")
    public Header<?> getConductedTests(@PathVariable UUID talentId) {
        return Header.ok(conductedTestService.getAllConductedTests(talentId));
    }
}
