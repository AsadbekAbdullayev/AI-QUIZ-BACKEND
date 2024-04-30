package uz.genesis.aiquest.webserver.clients;

import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
public class RapidApiAuthorizationConf {
    @Value(value = "${external.linkedin.api-host}")
    private String apiHost;

    @Value(value = "${external.linkedin.api-key}")
    private String apiKey;


    @Bean
    public RequestInterceptor authInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-RapidAPI-Key", apiKey);
            requestTemplate.header("X-RapidAPI-Host", apiHost);
        };
    }
}
