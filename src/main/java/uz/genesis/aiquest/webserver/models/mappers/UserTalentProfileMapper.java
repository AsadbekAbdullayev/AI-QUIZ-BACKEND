package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.genesis.aiquest.webserver.models.dto.TalentProfileDTO;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring")
public interface UserTalentProfileMapper extends GenericMapper<UserTalent, TalentProfileDTO> {

    @Mappings(
            {

                    @Mapping(
                            target = "subDirectionCaption",
                            source = "subDirection.caption"
                    ),
                    @Mapping(
                            target = "directionCaption",
                            source = "subDirection.direction.caption"
                    ),
                    @Mapping(
                            target ="directionId",
                            source = "subDirection.direction.id"
                    )
            }

    )
    TalentProfileDTO toDto(UserTalent entity);
}
