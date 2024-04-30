package uz.genesis.aiquest.webserver.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "recaptcha")
public record RecaptchaProperties(String verifyUrl, String secretKey) {
}