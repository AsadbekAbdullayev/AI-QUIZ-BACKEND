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
public class OneIdTokenResponseDTO {
    private String scope;

    @JsonProperty(value = "expires_in")
    private long expiresIn;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "access_token")
    private String accessToken;
}
