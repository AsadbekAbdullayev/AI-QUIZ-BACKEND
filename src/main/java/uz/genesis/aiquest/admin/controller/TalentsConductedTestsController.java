package uz.genesis.aiquest.admin.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.admin.dto.projection.ConductedTestProjection;
import uz.genesis.aiquest.webserver.models.dto.CustomPage;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.admin.service.TalentsConductedTestsService;
import uz.genesis.aiquest.webserver.models.dto.req.PaginationRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.TM_BASE_ADMIN_URL + "/talent/conducted-tests")
@SecurityRequirement(name = "admin")
public class TalentsConductedTestsController {
    private final TalentsConductedTestsService talentsConductedTestsService;

    @GetMapping
    public Header<List<ConductedTestProjection>> getResultOfTalent(@RequestParam(name = "scoreAsc", defaultValue = "false") Boolean scoreAsc, PaginationRequest paginationRequest) {
        CustomPage<ConductedTestProjection> conductedTestsOfTalents = talentsConductedTestsService.getConductedTestsOfTalents(paginationRequest, scoreAsc);
        return Header.ok(conductedTestsOfTalents.getData(), conductedTestsOfTalents.getPaginationData());
    }

}
