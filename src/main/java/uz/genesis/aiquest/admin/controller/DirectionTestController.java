package uz.genesis.aiquest.admin.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.genesis.aiquest.admin.repository.DirectionTestRepository;
import uz.genesis.aiquest.webserver.models.dto.DirectionTestDTO;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.commons.basecontroller.GenericController;
import uz.genesis.aiquest.webserver.commons.baseservice.GenericService;
import uz.genesis.aiquest.webserver.models.entity.DirectionTest;
import uz.genesis.aiquest.webserver.models.mappers.DirectionTestMapper;

@RestController
@RequestMapping(value = RestConstants.TM_BASE_ADMIN_URL + "/direction-tests")
@SecurityRequirement(name = "admin")
public class DirectionTestController extends GenericController<DirectionTest, Long, DirectionTestDTO, DirectionTestRepository, DirectionTestMapper> {

    public DirectionTestController(GenericService<DirectionTest, Long, DirectionTestDTO, DirectionTestRepository, DirectionTestMapper> service) {
        super(service);
    }
}
