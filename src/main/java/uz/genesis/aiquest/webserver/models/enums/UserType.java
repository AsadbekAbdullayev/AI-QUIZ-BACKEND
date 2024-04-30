package uz.genesis.aiquest.webserver.models.enums;

import uz.genesis.aiquest.webserver.commons.security.JwtTokenObject;
import uz.genesis.aiquest.webserver.commons.security.TokenProperties;

import java.util.Map;

public enum UserType {
    USER_TALENT(
            new TokenProperties(
                    3 * 24 * 60 * 60 * 1000,
                    "ZmQwYTgzYWVhNzgwY2RmNzRiMjBiMGJmZjUxMGFmZmF0YWxlbnRtYW5hZ2VtZW50"

            )
    ), ADMIN(new TokenProperties(
            3 * 24 * 60 * 60 * 1000
            , "d2FpdGNob3NlbnRvdGFsZ29vc2VhZGplY3RpdmVzdWJzdGFuY2VwZW5yZWdpb25iZW4="
    ));

    private final TokenProperties tokenProperties;

    UserType(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public TokenProperties getTokenProperties() {
        return tokenProperties;
    }

    public JwtTokenObject createJwtTokenObject(String token) {
        return new JwtTokenObject(tokenProperties.getExpiresInMillis(), tokenProperties.getSecretKey(), token);
    }

    public JwtTokenObject createJwtTokenObjectWithClaims(String sub, Map<String, Object> claims) {
        return new JwtTokenObject(tokenProperties.getExpiresInMillis(), tokenProperties.getSecretKey(), sub, claims);
    }
}
