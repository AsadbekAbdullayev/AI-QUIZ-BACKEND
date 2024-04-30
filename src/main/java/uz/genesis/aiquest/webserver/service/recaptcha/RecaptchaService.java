package uz.genesis.aiquest.webserver.service.recaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uz.genesis.aiquest.webserver.models.record.RecaptchaResponse;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret-key}")
    private String secretKey;
    @Value("${recaptcha.verify-url}")
    private String verifyUrl;
    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RecaptchaResponse validateToken(String recaptchaToken) {

        // https://www.google.com/recaptcha/api/siteverify METHOD: POST
        // secret	Required. The shared key between your site and reCAPTCHA.
        // response Required. The user response token provided by the reCAPTCHA client-side integration on your site.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response",recaptchaToken);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(map,headers);
        ResponseEntity<RecaptchaResponse> response = restTemplate.exchange(verifyUrl,
                HttpMethod.POST,
                entity,
                RecaptchaResponse.class);

        return response.getBody();
    }
}
