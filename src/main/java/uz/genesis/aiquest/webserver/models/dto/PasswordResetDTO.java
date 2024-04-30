package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetDTO {
    private String newPassword;
    private String newPasswordConfirmation;
}
