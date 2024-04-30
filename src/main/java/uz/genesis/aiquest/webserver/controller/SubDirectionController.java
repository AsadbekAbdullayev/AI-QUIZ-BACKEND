package uz.genesis.aiquest.webserver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.genesis.aiquest.webserver.commons.baseservice.GenericService;
import uz.genesis.aiquest.webserver.models.dto.SubDirectionDTO;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.commons.basecontroller.GenericController;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;
import uz.genesis.aiquest.webserver.models.mappers.SubDirectionMapper;

@RestController
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/sub-directions")
@SecurityRequirement(name = "talent")
public class SubDirectionController extends GenericController<SubDirection, Long, SubDirectionDTO, SubDirectionRepository, SubDirectionMapper> {


    public SubDirectionController(GenericService<SubDirection, Long, SubDirectionDTO, SubDirectionRepository, SubDirectionMapper> service) {
        super(service);
    }
}
