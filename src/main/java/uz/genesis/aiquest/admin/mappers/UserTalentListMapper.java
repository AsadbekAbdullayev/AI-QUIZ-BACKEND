package uz.genesis.aiquest.admin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.genesis.aiquest.admin.dto.AdminUserTalentDTO;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;


@Mapper(componentModel = "spring")
public interface UserTalentListMapper extends GenericMapper<UserTalent, AdminUserTalentDTO> {

    @Mappings(
            {
                    @Mapping(
                            source = "subDirection.caption",
                            target = "subDirectionCaption"
                    ),
                    @Mapping(
                            source = "subDirection.direction.caption",
                            target = "directionCaption"
                    )
            }
    )

    @Override
    AdminUserTalentDTO toDto(UserTalent entity);
}
