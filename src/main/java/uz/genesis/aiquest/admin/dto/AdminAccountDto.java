package uz.genesis.aiquest.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;
import uz.genesis.aiquest.webserver.models.enums.EAccountRole;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAccountDto extends BaseDTO<UUID> {
    @NotNull(message = "firstName" + ErrorMessages.SHOULDNT_BE_NULL)
    private String firstName;
    @NotNull(message = "email" + ErrorMessages.SHOULDNT_BE_NULL)
    private String email;
    @NotNull(message = "lastName" + ErrorMessages.SHOULDNT_BE_NULL)
    private String lastName;
    @NotNull(message = "password" + ErrorMessages.SHOULDNT_BE_NULL)
    private String password;
    @NotNull(message = "accountRole" + ErrorMessages.SHOULDNT_BE_NULL)
    private EAccountRole accountRole;
}

