package uz.genesis.aiquest.webserver.models.dto.res;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtTokenResponse {
    private String accessToken;
}
