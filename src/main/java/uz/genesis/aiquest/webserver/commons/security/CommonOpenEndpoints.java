package uz.genesis.aiquest.webserver.commons.security;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.List.of;

public interface CommonOpenEndpoints {

    List<String> swaggerPaths = List.of("/swagger-ui/**",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html"
    );


    String SIGN_UP = RestConstants.TM_BASE_TALENT_URL + "/auth/sign-up";
    String VERIFY = RestConstants.TM_BASE_TALENT_URL + "/auth/activate/**";
    String SIGN_IN = RestConstants.TM_BASE_TALENT_URL + "/auth/sign-in";
    String REGIONS = RestConstants.TM_BASE_TALENT_URL + "/regions/**";
    String DIRECTIONS = RestConstants.TM_BASE_TALENT_URL + "/directions/**";
    String SUBDIRECTIONS = RestConstants.TM_BASE_TALENT_URL + "/sub-directions/**";
    String CHECK_EMAIL = RestConstants.TM_BASE_TALENT_URL + "/auth/check-email";
    String PASSWORD_RECOVERY = RestConstants.TM_BASE_TALENT_URL + "/auth/password-recovery";
    String RESET_PASSWORD = RestConstants.TM_BASE_TALENT_URL + "/auth/reset-password/**";

    String HR_SIGN_UP = RestConstants.TM_BASE_TALENT_URL + "/auth/hr/sign-up";
    String HR_SIGN_IN = RestConstants.TM_BASE_TALENT_URL + "/auth/sign-in";

    String HR_POSITIONS = RestConstants.TM_BASE_TALENT_URL + "/hr/positions/**";

    String ADMIN_AUTH_SIGN_IN = RestConstants.TM_BASE_ADMIN_URL + "/auth/sign-in";
    String HR_AUTH_SIGN_IN = RestConstants.TM_BASE_HR_URL + "/auth/sign-in";
    String HR_AUTH_SIGN_UP = RestConstants.TM_BASE_HR_URL + "/auth/sign-up";
    String VERIFY_HR = RestConstants.TM_BASE_HR_URL + "/auth/activate/**";
    String HR_CHECK_EMAIL = RestConstants.TM_BASE_HR_URL + "/auth/check-email";
    String HR_PASSWORD_RECOVERY = RestConstants.TM_BASE_HR_URL + "/auth/password-recovery";
    String HR_RESET_PASSWORD = RestConstants.TM_BASE_HR_URL + "/auth/reset-password/**";
    String GET_INFO_BY_SUB_DIRECTION = RestConstants.TM_BASE_TALENT_URL + "/base";

    String GET_FAQ_COMMON_API = RestConstants.TM_BASE_TALENT_URL + "/base/FAQ";
//    Both side Talent and Admin can use file download url
    String FILE_DOWNLOAD = RestConstants.TM_BASE_URL + "/file/download/**";

    String BASE_APIS = RestConstants.TM_BASE_URL + "/base/**";

    String CALL_REQUEST_SEND = RestConstants.TM_BASE_URL + "/base/call-request";
    String SWAGGER_AUTH = "/swagger-auth";
    String OAUTH_GET_CONSENT_SCREEN_URL = RestConstants.TM_BASE_TALENT_URL + "/auth/oauth/**";
    String OAUTH_GET_CONSENT_SCREEN_URL_HR = RestConstants.TM_BASE_HR_URL + "/auth/oauth/**";
    Map<HttpMethod, List<String>> OPEN_ENDPOINTS = Map.of(
            HttpMethod.POST, of(
                    SIGN_UP,
                    SIGN_IN,
                    HR_AUTH_SIGN_UP,
                    HR_SIGN_IN,
                    HR_SIGN_UP,
                    RESET_PASSWORD,
                    HR_RESET_PASSWORD,
                    ADMIN_AUTH_SIGN_IN,
                    HR_AUTH_SIGN_IN,
                    CALL_REQUEST_SEND,
                    OAUTH_GET_CONSENT_SCREEN_URL,
                    OAUTH_GET_CONSENT_SCREEN_URL_HR
            ),
            HttpMethod.DELETE, of(),
            HttpMethod.GET, of(
                    SWAGGER_AUTH,
                    CHECK_EMAIL,
                    VERIFY,
                    SUBDIRECTIONS,
                    DIRECTIONS,
                    REGIONS,
                    FILE_DOWNLOAD,
                    PASSWORD_RECOVERY,
                    HR_PASSWORD_RECOVERY,
                    GET_INFO_BY_SUB_DIRECTION,
                    BASE_APIS,
                    GET_FAQ_COMMON_API,
                    VERIFY_HR,
                    HR_POSITIONS,
                    HR_CHECK_EMAIL,
                    OAUTH_GET_CONSENT_SCREEN_URL,
                    OAUTH_GET_CONSENT_SCREEN_URL_HR
            ),
            HttpMethod.PUT, of()
    );
    default List<String> getByMethod(HttpMethod method) {
        var endpoints = OPEN_ENDPOINTS.get(method);
        return Objects.nonNull(endpoints) ? endpoints : Collections.emptyList();
    }

    default String[] getByMethodArr(HttpMethod method) {
        return getByMethod(method).toArray(new String[]{});
    }

    default String[] getPostOpenEndpoints() {
        return getByMethodArr(HttpMethod.POST);
    }

    default String[] getGetOpenEndpoints() {
        return getByMethodArr(HttpMethod.GET);
    }

    default boolean isRequestForSwagger(String servletPath) {
        return swaggerPaths.stream().anyMatch(pattern -> new AntPathMatcher().match(pattern, servletPath));
    }

}
