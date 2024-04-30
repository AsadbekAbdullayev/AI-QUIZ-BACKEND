package uz.genesis.aiquest.webserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.host}")
    private String mailServerHost;

    @Value("${spring.mail.port}")
    private Integer mailServerPort;

    @Value("${spring.mail.username}")
    private String mailServerUsername;

    @Value("${spring.mail.password}")
    private String mailServerPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailServerAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;

    @Value("${spring.mail.debug}")
    private String mailSenderDebug;

    @Value("${spring.mail.properties.ssl.enable}")
    private String sslEnable;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailServerHost);
        javaMailSender.setPort(mailServerPort);
        javaMailSender.setUsername(mailServerUsername);
        javaMailSender.setPassword(mailServerPassword);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth",mailServerAuth);
        properties.setProperty("mail.smtp.starttls.enable",mailServerStartTls);
        properties.setProperty("mail.smtp.ssl.enable",sslEnable);
        properties.setProperty("mail.debug",mailSenderDebug);
        return javaMailSender;
    }
}
