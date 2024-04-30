package uz.genesis.aiquest.webserver.service;

import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.repository.DirectionTestRepository;
import uz.genesis.aiquest.webserver.models.dto.SubDirectionDTO;
import uz.genesis.aiquest.webserver.commons.baseservice.GenericService;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;
import uz.genesis.aiquest.webserver.models.mappers.SubDirectionMapper;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;

import java.util.List;

@Service
public class SubDirectionService extends GenericService<SubDirection, Long, SubDirectionDTO, SubDirectionRepository, SubDirectionMapper> {

    private final DirectionTestRepository directionTestRepository;

    public SubDirectionService(SubDirectionRepository repository, SubDirectionMapper mapper, DirectionTestRepository directionTestRepository) {
        super(repository, mapper, SubDirection.class);
        this.directionTestRepository = directionTestRepository;
    }

    @Override
    public List<SubDirectionDTO> getList() {
        var subdirectionList = super.getList();
        subdirectionList.forEach(subDirectionDTO -> subDirectionDTO.setIsTestAdded(directionTestRepository.existsBySubDirectionId(subDirectionDTO.getId())));
        return subdirectionList;
    }
}
