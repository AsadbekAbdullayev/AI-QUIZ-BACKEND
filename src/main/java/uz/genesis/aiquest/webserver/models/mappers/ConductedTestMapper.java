package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.genesis.aiquest.webserver.models.dto.ConductedTestDTO;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;
import uz.genesis.aiquest.webserver.models.entity.ConductedTest;

@Mapper(componentModel = "spring", uses = GeneratedTestMapper.class)
public interface ConductedTestMapper extends GenericMapper<ConductedTest, ConductedTestDTO> {

    @Mappings(
            {
                    @Mapping(
                            source = "subDirection.caption",
                            target = "subDirectionCaption"
                    )
            }
    )
    @Override
    ConductedTestDTO toDto(ConductedTest entity);
}
