package uz.genesis.aiquest.webserver.models.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Oauth2TokenResponseDTO {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_int")
    private Integer expiresIn;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "refresh_token_expires_int")
    private Integer refreshTokenExpiresIn;

    @JsonProperty(value = "scope")
    private String scope;

    @JsonProperty(value = "id_token")
    private String idToken;

}
