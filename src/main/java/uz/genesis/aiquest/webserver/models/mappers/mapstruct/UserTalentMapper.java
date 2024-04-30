package uz.genesis.aiquest.webserver.models.mappers.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.genesis.aiquest.webserver.models.dto.UserTalentDTO;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;

@Mapper(componentModel = "spring")
public interface UserTalentMapper extends GenericMapper<UserTalent, UserTalentDTO> {
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
                            target = "profilePhotoURL",
                            source = "profilePhotoUrl"
                    )
            }
    )
    @Override
    UserTalentDTO toDto(UserTalent entity);
}
