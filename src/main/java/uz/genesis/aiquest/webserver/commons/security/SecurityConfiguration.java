package uz.genesis.aiquest.webserver.commons.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.genesis.aiquest.admin.dto.AdminAccountAuthenticationToken;
import uz.genesis.aiquest.admin.service.AdminDetailsService;
import uz.genesis.aiquest.webserver.commons.security.filter.JwtFilter;
import uz.genesis.aiquest.webserver.commons.security.filter.RecaptchaFilter;
import uz.genesis.aiquest.webserver.service.TalentDetailsService;
import uz.genesis.aiquest.webserver.service.recaptcha.RecaptchaService;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity(proxyTargetClass = true)
@RequiredArgsConstructor
public class SecurityConfiguration implements CommonOpenEndpoints {

    private final JwtFilter jwtFilter;
    private final TalentDetailsService talentDetailsService;

    private final AdminDetailsService adminDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RecaptchaService recaptchaService) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.GET, getGetOpenEndpoints())
                            .permitAll()
                            .requestMatchers(HttpMethod.POST, getPostOpenEndpoints())
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
//                .oauth2Login(withDefaults())
//                .formLogin(withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RecaptchaFilter(recaptchaService),UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(@Qualifier(value = "userTalentAuthenticationProvider") AuthenticationProvider talentAuthenticationProvider
            , @Qualifier(value = "adminAuthenticationProvider") AuthenticationProvider adminAuthenticationProvider) {
        return new ProviderManager(
                talentAuthenticationProvider,
                adminAuthenticationProvider
        );
    }


    @Bean(name = "userTalentAuthenticationProvider")
    public DaoAuthenticationProvider userTalentAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider() {
            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.getName().equals(UsernamePasswordAuthenticationToken.class.getName());
            }
        };
        daoAuthenticationProvider.setUserDetailsService(talentDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean(name = "adminAuthenticationProvider")
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider() {
            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.getName().equals(AdminAccountAuthenticationToken.class.getName());
            }
        };
        daoAuthenticationProvider.setUserDetailsService(adminDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
