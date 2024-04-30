package uz.genesis.aiquest.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.models.dto.SignInDTO;
import uz.genesis.aiquest.webserver.models.dto.res.JwtTokenResponse;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.admin.dto.AdminAccountAuthenticationToken;
import uz.genesis.aiquest.webserver.commons.exception.UnauthorizedException;
import uz.genesis.aiquest.webserver.commons.security.JwtConfigurer;
import uz.genesis.aiquest.webserver.commons.security.JwtTokenObject;
import uz.genesis.aiquest.webserver.models.entity.AdminAccount;
import uz.genesis.aiquest.webserver.models.enums.UserType;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Log4j2
public class AdminAccountAuthService {

    private final AuthenticationManager authenticationManager;


    public JwtTokenResponse authenticateAdmin(SignInDTO signInDTO) {
        try {
            var authentication = authenticationManager.authenticate(new AdminAccountAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));
            var adminAccount = (AdminAccount) authentication.getPrincipal();
            JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.ADMIN,adminAccount.getId().toString(), signInDTO.getEmail(), Map.of(
                    RestConstants.EMAIL_KEY, adminAccount.getEmail()
            ));
            var token = jwtTokenObject.getToken();
            return JwtTokenResponse.builder()
                    .accessToken(token)
                    .build();
        }catch (Exception e){
            log.error("Exception occurred while authenticating admin ", e);
            throw new UnauthorizedException("email_or_password_error", ErrorCodes.ER_UNAUTHORIZED);
        }
    }
}
