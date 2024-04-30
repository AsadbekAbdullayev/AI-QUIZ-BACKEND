package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTalentAuthDTO {
    @NotBlank(message = "firstName " + ErrorMessages.SHOULDNT_BE_EMPTY)
    private String firstName;
    @NotNull(message = "email " + ErrorMessages.SHOULDNT_BE_NULL)
    @Email
    private String email;

    @NotNull(message = "password " + ErrorMessages.SHOULDNT_BE_NULL)
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
}
