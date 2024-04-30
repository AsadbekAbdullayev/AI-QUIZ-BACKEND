package uz.genesis.aiquest.admin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAccountUpdatePasswordDto {
    @NotNull(message = "oldPassword" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size(min = 8, message = "oldPassword required min length is 8")
    private String oldPassword;
    @NotNull(message = "newPassword" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size(min = 8, message = "newPassword required min length is 8")
    private String newPassword;
}
