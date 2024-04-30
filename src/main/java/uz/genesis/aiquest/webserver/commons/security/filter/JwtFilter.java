package uz.genesis.aiquest.webserver.commons.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.genesis.aiquest.admin.service.AdminDetailsService;
import uz.genesis.aiquest.webserver.commons.exception.JwtNotValidException;
import uz.genesis.aiquest.webserver.commons.exception.UnauthorizedException;
import uz.genesis.aiquest.webserver.commons.security.CommonOpenEndpoints;
import uz.genesis.aiquest.webserver.commons.security.JwtConfigurer;
import uz.genesis.aiquest.webserver.commons.security.JwtTokenObject;
import uz.genesis.aiquest.webserver.component.Localization;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.enums.UserType;
import uz.genesis.aiquest.webserver.service.TalentDetailsService;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.utils.Utils;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter implements CommonOpenEndpoints {
    private final TalentDetailsService talentDetailsService;
    private final AdminDetailsService adminDetailsService;
    private final ObjectMapper objectMapper;
    private final Localization localization;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (customShouldNotFilter(request)) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.error(e);
                Utils.writerErrorResp(e, response, HttpServletResponse.SC_CONFLICT, ErrorCodes.ERR_INTERNAL_SERVER, objectMapper);
            }
        } else {
            String bearerToken = new AuthorizationExtractor(request).getAuthorizationHeaderValue();
            try {
                if (bearerToken != null && bearerToken.startsWith(RestConstants.BEARER) ) {
                    String token = bearerToken.substring(7);

                    if (token.equals("null")) {
                        throw new UnauthorizedException("forbidden", ErrorCodes.ERR_FORBIDDEN_SCOPE);
                    }

                    var userType = Utils.getUserType(token);
                    boolean isMatched = isAdminHavePermissionToPath(userType, request.getServletPath());
                    if (isMatched) {
                        authorizeByType(UserType.valueOf(userType), token);
                    } else throw new UnauthorizedException("forbidden", ErrorCodes.ERR_FORBIDDEN_SCOPE);
                }

                filterChain.doFilter(request, response);
            } catch (JwtNotValidException e) {
                log.error(e);
                Utils.writerErrorResp(e, response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodes.ER_UNAUTHORIZED, objectMapper);
            } catch (UnauthorizedException e) {
                log.error("Exception occurred while request being filtered {0}", e);
                if (e.getErrorCode().equals(ErrorCodes.ERR_BLOCKED_USER)) {
                    Utils.writerErrorResp(e, response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodes.ERR_BLOCKED_USER, objectMapper);
                } else
                    Utils.writerErrorResp(e, response, HttpServletResponse.SC_FORBIDDEN, e.getErrorCode(), objectMapper);

            } catch (Exception e) {
                log.error(e);
                Utils.writerErrorResp(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorCodes.ERROR, objectMapper);
            }
        }

    }

    private Boolean isAdminHavePermissionToPath(String userType, String servletPath) {
        if (UserType.ADMIN.name().equals(userType)) {
            return servletPath.contains(RestConstants.TM_BASE_ADMIN_URL) || isRequestForSwagger(servletPath);
        } else if (UserType.USER_TALENT.name().equals(userType)) {
            return servletPath.contains(RestConstants.TM_BASE_TALENT_URL); //if request came from user to admin apis, we should return false
        }
        return false;
    }

    private void authorizeByType(UserType userType, String token) {
        JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(userType, token);

        if (jwtTokenObject.isExpired()) {
            throw new UnauthorizedException("error_during_authorization", ErrorCodes.ER_UNAUTHORIZED);
        }

        if (userType.equals(UserType.USER_TALENT)) {

            var uuid = jwtTokenObject.getSubject();
            var userTalent = (UserTalent) talentDetailsService.loadUserByUsername(uuid);

            if (!userTalent.isEnabled()) {
                throw new UnauthorizedException(localization.getMessage("user_is_blocked"), ErrorCodes.ERR_BLOCKED_USER);
            }

            putToContext(userTalent);
        } else if (userType.equals(UserType.ADMIN)) {
            var email = jwtTokenObject.getClaimByKey(RestConstants.EMAIL_KEY);
            var admin = (UserDetails) adminDetailsService.loadUserByUsername(email);
            putToContext(admin);
        }
    }

    private void putToContext(UserDetails userDetails) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }
    protected boolean customShouldNotFilter(HttpServletRequest request) {
        return OPEN_ENDPOINTS.values().stream().flatMap(Collection::stream).anyMatch(endpoint -> new AntPathMatcher().match(endpoint, request.getServletPath()));
    }
}
