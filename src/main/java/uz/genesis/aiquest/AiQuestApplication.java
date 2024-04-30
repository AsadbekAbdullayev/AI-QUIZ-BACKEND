package uz.genesis.aiquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import uz.genesis.aiquest.webserver.config.RecaptchaProperties;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties(RecaptchaProperties.class)
public class AiQuestApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AiQuestApplication.class, args);
    }

}
