package uz.genesis.aiquest.webserver.service;

import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.repository.DirectionTestRepository;
import uz.genesis.aiquest.webserver.commons.baseservice.Deletable;
import uz.genesis.aiquest.webserver.commons.baseservice.GenericService;
import uz.genesis.aiquest.webserver.models.dto.DirectionDTO;
import uz.genesis.aiquest.webserver.repository.DirectionRepository;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;
import uz.genesis.aiquest.webserver.models.entity.Direction;
import uz.genesis.aiquest.webserver.models.mappers.DirectionMapper;

import java.util.List;

@Service
public class DirectionService extends GenericService<Direction,Long, DirectionDTO, DirectionRepository, DirectionMapper> implements Deletable {

    private final DirectionTestRepository directionTestRepository;
    public DirectionService(DirectionRepository repository, DirectionMapper mapper, SubDirectionRepository subDirectionRepository, DirectionTestRepository directionTestRepository) {
        super(repository, mapper, Direction.class);
        this.directionTestRepository = directionTestRepository;
    }

    @Override
    public DirectionDTO create(DirectionDTO dto) {
        var direction = mapper.toEntity(dto);
        direction.getSubDirections().forEach(subDirection -> subDirection.setDirection(direction));
        var saved = repository.save(direction);
        return mapper.toDto(saved);
    }

    public DirectionDTO modify(Boolean activate, Long directionId) {
        var direction = findById(directionId);
        direction.setIsActive(activate);
        var savedDirection = repository.save(direction);
        return mapper.toDto(savedDirection);
    }

    @Override
    public List<DirectionDTO> getList() {
        var directions = super.getList();
        directions.forEach(directionDTO -> directionDTO.getSubDirections().forEach(
                subDirectionDTO -> subDirectionDTO.setIsTestAdded(directionTestRepository.existsBySubDirectionId(subDirectionDTO.getId()))
        ));
        return directions;
    }
}

