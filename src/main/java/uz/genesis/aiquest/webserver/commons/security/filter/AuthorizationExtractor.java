package uz.genesis.aiquest.webserver.commons.security.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import uz.genesis.aiquest.webserver.commons.security.CommonOpenEndpoints;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.Arrays;
import java.util.Objects;

public class AuthorizationExtractor implements CommonOpenEndpoints {

    private final HttpServletRequest httpServletRequest;

    public AuthorizationExtractor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }


    public String getAuthorizationHeaderValue() {
        if (isRequestForSwagger(httpServletRequest.getServletPath())) {

            if (Objects.isNull(httpServletRequest.getCookies())) {
                return null;
            }
            Cookie token = Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> cookie.getName().equals("token"))
                    .findFirst()
                    .orElse(null);
            if (token != null)
                return RestConstants.BEARER + token.getValue();
            return null;
        }
        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
