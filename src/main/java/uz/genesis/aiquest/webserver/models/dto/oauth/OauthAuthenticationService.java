package uz.genesis.aiquest.webserver.models.dto.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.genesis.aiquest.webserver.models.enums.EOauth2Provider;

import java.net.URI;

@Log4j2
@Service
@RequiredArgsConstructor
public class OauthAuthenticationService {

    private final RestTemplate restTemplate;


    public String getOIDCToken(EOauth2Provider eOauth2Provider, String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = getMultiValueMapHttpEntity(eOauth2Provider, authorizationCode, headers);

        try {
            Oauth2TokenResponseDTO oauth2TokenResponseDTO = restTemplate.postForObject(
                    eOauth2Provider.getAccessTokenUrl(),
                    requestEntity,
                    Oauth2TokenResponseDTO.class
            );
            if (oauth2TokenResponseDTO != null)
                return oauth2TokenResponseDTO.getIdToken();

            throw new UnsupportedOperationException("Something went wrong");
        } catch (Exception e) {
            log.error(e);
            throw new UnsupportedOperationException("Something went wrong");
        }

    }

    public String getOneIdAccessToken(EOauth2Provider provider, String authorizationCode) {
        try {
            OneIdTokenResponseDTO oneIdTokenResponseDTO = restTemplate.postForObject(UriComponentsBuilder.fromHttpUrl(provider.getAccessTokenUrl())
                            .queryParams(getOneIdParamsForTokenEndpoint(provider, authorizationCode)).build().toUri(),
                    null,
                    OneIdTokenResponseDTO.class);
            if (oneIdTokenResponseDTO != null)
                return oneIdTokenResponseDTO.getAccessToken();

            throw new NullPointerException("Something went wrong, oneIdTokenResponseDTO is null");
        } catch (Exception e) {
            log.error(e);
            throw new UnsupportedOperationException("Something went wrong");
        }

    }


    public OneIdUserInfoResponseDTO getUserInfoFromOneId(EOauth2Provider provider, String authorizationCode) {
        String oneIdAccessToken = getOneIdAccessToken(provider, authorizationCode);
        MultiValueMap<String, String> oneIdParamsAsMap = getOneIdParamsForUserInfoEndpoint(provider, authorizationCode,oneIdAccessToken);
        URI uri = UriComponentsBuilder.fromHttpUrl(EOauth2Provider.ONE_ID_USER_ENDPOINT)
                .queryParams(oneIdParamsAsMap)
                .build().toUri();
        return restTemplate.postForObject(uri, null, OneIdUserInfoResponseDTO.class);
    }


    private MultiValueMap<String, String> getOneIdParamsForTokenEndpoint(EOauth2Provider provider, String authorizationCode) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "one_authorization_code");
        requestParams.add("code", authorizationCode);
        requestParams.add("client_id", provider.getClientId());
        requestParams.add("client_secret", provider.getClientSecret());
        requestParams.add("redirect_uri", EOauth2Provider.getRedirectURLBasedOnUser());
        return requestParams;
    }

    private MultiValueMap<String, String> getOneIdParamsForUserInfoEndpoint(EOauth2Provider provider, String authorizationCode, String oneIdAccessToken) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "one_access_token_identify");
        requestParams.add("code", authorizationCode);
        requestParams.add("client_id", provider.getClientId());
        requestParams.add("client_secret", provider.getClientSecret());
        requestParams.add("redirect_uri", EOauth2Provider.getRedirectURLBasedOnUser());
        requestParams.add("access_token", oneIdAccessToken);
        return requestParams;
    }


    private HttpEntity<MultiValueMap<String, String>> getMultiValueMapHttpEntity(EOauth2Provider eOauth2Provider, String authorizationCode, HttpHeaders headers) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", eOauth2Provider.getClientId());
        requestBody.add("client_secret", eOauth2Provider.getClientSecret());
        requestBody.add("redirect_uri", EOauth2Provider.getRedirectURLBasedOnUser());
        return new HttpEntity<>(requestBody, headers);
    }


}
