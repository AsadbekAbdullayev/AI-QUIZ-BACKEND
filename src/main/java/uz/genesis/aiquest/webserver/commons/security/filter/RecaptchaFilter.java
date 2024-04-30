package uz.genesis.aiquest.webserver.commons.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.genesis.aiquest.webserver.service.recaptcha.RecaptchaService;

import java.io.IOException;

public class RecaptchaFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(RecaptchaFilter.class);
    private final RecaptchaService recaptchaService;

    public RecaptchaFilter(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        if (request.getMethod().equals("POST") && request.getServletPath().endsWith("/admin/auth/sign-in")) {
//            String recaptcha = request.getHeader("recaptcha");
//            RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
//            if (!recaptchaResponse.success()) {
//                LOG.info("Invalid reCAPTCHA token");
//                throw new BadCredentialsException("Invalid reCaptcha token");
//            }
//        }
        filterChain.doFilter(request, response);
    }

}
