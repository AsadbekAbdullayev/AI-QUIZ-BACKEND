package uz.genesis.aiquest.webserver.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.enums.EOauth2Provider;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignInByOauthProviderDTO {

    @NotNull(message = "authorizationCode " + ErrorMessages.SHOULDNT_BE_NULL)
    private String authorizationCode;


    @NotNull(message = "ouathProvider " + ErrorMessages.SHOULDNT_BE_NULL)
    @JsonProperty(value = "ouathProvider")
    private EOauth2Provider eOauth2Provider;

}
