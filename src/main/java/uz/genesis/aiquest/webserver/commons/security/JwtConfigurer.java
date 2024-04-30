package uz.genesis.aiquest.webserver.commons.security;

import lombok.Getter;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.models.enums.UserType;

import java.util.HashMap;
import java.util.Map;

@Getter
public class JwtConfigurer {
    private final JwtTokenObject jwtTokenObject;

    private JwtConfigurer(UserType userType, String token) {
        jwtTokenObject = userType.createJwtTokenObject(token);
    }

    public static JwtTokenObject getJwtTokenObject(UserType userType, String token) {
        return new JwtConfigurer(userType, token).getJwtTokenObject();
    }

    public static JwtTokenObject getJwtTokenObject(UserType userType, String subject, String email, Map<String, Object> claims) {
        Map<String, Object> claimsMutable = new HashMap<>(claims);
        claimsMutable.put(RestConstants.USER_TYPE, userType);
        claimsMutable.put(RestConstants.EMAIL_KEY, email);
        return userType.createJwtTokenObjectWithClaims(subject, claimsMutable);
    }
}
