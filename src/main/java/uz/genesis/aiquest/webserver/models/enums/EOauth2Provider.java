package uz.genesis.aiquest.webserver.models.enums;

import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.utils.Utils;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public enum EOauth2Provider {

    GOOGLE("https://oauth2.googleapis.com/token",
            "289700683844-ojqf2p1k3mfvdg814fcs54qevub8ebt1.apps.googleusercontent.com",
            System.getenv("GOOGLE_CLIENT_SECRET"),
            List.of("openid", "profile", "email"),
            "https://accounts.google.com/o/oauth2/v2/auth", "code"),



    LINKEDIN("https://www.linkedin.com/oauth/v2/accessToken", "784k1t7zxvzks8",
            System.getenv("LINKEDIN_CLIENT_SECRET"),
            List.of("openid", "profile", "email"),
            "https://www.linkedin.com/oauth/v2/authorization", "code"),


    ONE_ID("https://sso.egov.uz/sso/oauth/Authorization.do", "aiquest_uz", System.getenv("ONEID_CLIENT_SECRET"),
            List.of("myportal"),"https://sso.egov.uz/sso/oauth/Authorization.do", "one_code");

    public static final String REDIRECT_URL_TALENT = "https://aiquest.uz/success";
    public static final String REDIRECT_URL_HR = "https://hr.aiquest.uz/success";
    public static final String ONE_ID_USER_ENDPOINT = "https://sso.egov.uz/sso/oauth/Authorization.do";
    private final String accessTokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final List<String> scopes;
    private final String consentScreenBaseURL;
    private final String responseType;

    EOauth2Provider(String accessTokenUrl, String clientId, String clientSecret, List<String> scopes, String consentScreenBaseURL, String responseType) {
        this.accessTokenUrl = accessTokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.consentScreenBaseURL = consentScreenBaseURL;
        this.responseType = responseType;
    }

    public static String prettyPrint() {
        List<String> list = Arrays.stream(values()).map(Enum::name).toList();
        return String.format("Currently supported oauth Providers: %s", String.join(" , ", list));

    }

    public static Map<String, String> getAllAsMap() {
        return Arrays.stream(values()).collect(Collectors.toMap(Enum::name, EOauth2Provider::getConsentScreenURL));
    }

    public String spaceSeparatedScopes() {
        return String.join(" ", scopes);
    }

    public String getConsentScreenURL() {
        return UriComponentsBuilder.fromHttpUrl(consentScreenBaseURL)
                .queryParam("response_type", getResponseType())
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", getRedirectURLBasedOnUser())
                .queryParam("scope", spaceSeparatedScopes())
                .queryParam("state", UUID.randomUUID().toString())
                .toUriString();
    }

    public static String getRedirectURLBasedOnUser() {
        String requestPath = Utils.currentRequestPath();
        if (requestPath.startsWith(RestConstants.TM_BASE_HR_URL)) {
            return REDIRECT_URL_HR;
        } else if (requestPath.startsWith(RestConstants.TM_BASE_TALENT_URL))
            return REDIRECT_URL_TALENT;
        throw new GeneralApiException("app_is_not_configured_well");
    }

}
