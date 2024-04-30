package uz.genesis.aiquest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import uz.genesis.aiquest.admin.service.AdminAccountService;

@Component
@RequiredArgsConstructor
public class Seeder {

    private final AdminAccountService adminAccountService;

    @Bean
    @ConditionalOnProperty(value = "spring.sql.init.mode", havingValue = "always")
    public CommandLineRunner runner() {
        return args -> {
            adminAccountService.insertSuperAdmin();
        };
    }


}
