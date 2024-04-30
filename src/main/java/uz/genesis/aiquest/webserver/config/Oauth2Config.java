package uz.genesis.aiquest.webserver.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import uz.genesis.aiquest.webserver.models.enums.EOauth2Provider;

import java.util.Arrays;
import java.util.Objects;

@Configuration
@Log4j2
public class Oauth2Config {

    @PostConstruct
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void checkEnvironment() {
        boolean isClientSecretsNotGivenToEnv = Arrays.stream(EOauth2Provider.values())
                .anyMatch(oauth2Provider -> Objects.isNull(oauth2Provider.getClientSecret()));
        if (isClientSecretsNotGivenToEnv) {
            throw new IllegalStateException("Environment variable should be given" + EOauth2Provider.prettyPrint());
        }
    }
}
