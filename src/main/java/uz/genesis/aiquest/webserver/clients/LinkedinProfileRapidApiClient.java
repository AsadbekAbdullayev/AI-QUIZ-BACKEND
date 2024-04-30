package uz.genesis.aiquest.webserver.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.genesis.aiquest.webserver.clients.dto.LinkedinProfileResponse;

@FeignClient(value = "linkedin-rapid-api-client", url = "${external.linkedin.api-url}", configuration = RapidApiAuthorizationConf.class)
public interface LinkedinProfileRapidApiClient {

    @GetMapping
    LinkedinProfileResponse getProfile(@RequestParam(value = "username") String username);

}
