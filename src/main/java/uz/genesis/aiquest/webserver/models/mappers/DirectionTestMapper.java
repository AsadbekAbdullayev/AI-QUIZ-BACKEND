package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.genesis.aiquest.webserver.models.dto.DirectionTestDTO;
import uz.genesis.aiquest.webserver.models.entity.DirectionTest;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring")
public interface DirectionTestMapper extends GenericMapper<DirectionTest, DirectionTestDTO> {
    @Mappings(
            @Mapping(
                    source = "subDirection.caption",
                    target = "subDirectionCaption"
            )
    )
    @Override
    DirectionTestDTO toDto(DirectionTest entity);
}
