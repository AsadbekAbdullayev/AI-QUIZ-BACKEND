package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.SubDirectionDTO;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;

@Mapper(componentModel = "spring")
public interface SubDirectionMapper extends GenericMapper<SubDirection, SubDirectionDTO> {

}
