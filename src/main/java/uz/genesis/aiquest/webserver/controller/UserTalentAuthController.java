package uz.genesis.aiquest.webserver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.webserver.models.dto.*;
import uz.genesis.aiquest.webserver.models.dto.res.JwtTokenResponse;
import uz.genesis.aiquest.webserver.models.enums.EOauth2Provider;
import uz.genesis.aiquest.webserver.service.UserTalentAuthService;
import uz.genesis.aiquest.webserver.utils.RestConstants;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/auth")
@SecurityRequirement(name = "talent")
public class UserTalentAuthController {
    private final UserTalentAuthService userTalentAuthService;

    @PostMapping(value = "/sign-up")
    public Header<?> signUp(@RequestBody @Valid Header<UserTalentAuthDTO> userTalentDTOHeader) {
        userTalentAuthService.signUp(userTalentDTOHeader.getData());
        return Header.ok();
    }

    @GetMapping(value = "/activate/{token}")
    public Header<JwtTokenResponse> activateAccount(@PathVariable String token) {
        return Header.ok(userTalentAuthService.activateUser(token));
    }

    @PostMapping(value = "/sign-in")
    public Header<JwtTokenResponse> signIn(@RequestBody Header<SignInDTO> signInDTOHeader) {
        return Header.ok(userTalentAuthService.signIn(signInDTOHeader.getData()));
    }

    @GetMapping(value = "/check-email")
    public Header<Boolean> checkEmail(@RequestParam String email) {
        return Header.ok(userTalentAuthService.isEmailExist(email));
    }


    @GetMapping(value = "/password-recovery")
    public Header<?> recoverPassword(@RequestParam String email) {
        userTalentAuthService.recoverPassword(email);
        return Header.ok();
    }


    @PostMapping(value = "/reset-password/{token}")
    public Header<JwtTokenResponse> verifyAccount(@PathVariable String token,
                                                  @RequestBody Header<PasswordResetDTO> passwordResetDTOHeader) {
        return Header.ok(userTalentAuthService.resetPassword(token, passwordResetDTOHeader.getData()));
    }

    @PostMapping(value = "/oauth/send-code")
    public Header<JwtTokenResponse> signInByAuthorizationCode(@RequestBody @Valid Header<SignInByOauthProviderDTO> signInByOauthProviderDTOHeader) {
        JwtTokenResponse jwtTokenResponse = userTalentAuthService.signInByOauthProviders(signInByOauthProviderDTOHeader.getData());
        return Header.ok(jwtTokenResponse);
    }

    @GetMapping(value = "/oauth/supported-providers")
    public Header<Map<String, String>> getOauthURL() {
        return Header.ok(EOauth2Provider.getAllAsMap());
    }
}
