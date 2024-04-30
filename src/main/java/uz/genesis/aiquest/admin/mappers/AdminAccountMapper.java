package uz.genesis.aiquest.admin.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.admin.dto.AdminAccountDto;
import uz.genesis.aiquest.webserver.models.entity.AdminAccount;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring")
public interface AdminAccountMapper extends GenericMapper<AdminAccount, AdminAccountDto> {

}
