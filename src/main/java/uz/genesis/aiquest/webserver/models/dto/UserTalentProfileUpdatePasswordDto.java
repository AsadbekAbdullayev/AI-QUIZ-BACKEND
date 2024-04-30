package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.Objects;

@Getter
@Setter
public class UserTalentProfileUpdatePasswordDto {
    @NotNull(message = "oldPassword" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size(min = 8, message = "oldPassword required min length is 8")
    private String oldPassword;
    @NotNull(message = "newPassword" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size(min = 8, message = "newPassword required min length is 8")
    private String newPassword;

    @NotNull(message = "copyPassword" + ErrorMessages.SHOULDNT_BE_NULL)
    private String copyPassword;
    @AssertTrue(message = "copy password and passwords dont match")
    public boolean isPasswordAndCopyPasswordMatches() {
        return Objects.nonNull(newPassword) && Objects.nonNull(copyPassword) && newPassword.equals(copyPassword);
    }
}
