package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.DirectionDTO;
import uz.genesis.aiquest.webserver.models.entity.Direction;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring", uses = SubDirectionMapper.class)
public interface DirectionMapper extends GenericMapper<Direction, DirectionDTO> {

}
