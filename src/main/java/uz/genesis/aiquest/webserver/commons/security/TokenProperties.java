package uz.genesis.aiquest.webserver.commons.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenProperties {
    private Integer expiresInMillis;
    private String secretKey;

}
