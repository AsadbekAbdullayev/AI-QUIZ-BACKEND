package uz.genesis.aiquest.admin.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.models.dto.SignInDTO;
import uz.genesis.aiquest.webserver.models.dto.res.JwtTokenResponse;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.admin.service.AdminAccountAuthService;

@RestController
@RequestMapping(value = RestConstants.TM_BASE_ADMIN_URL + "/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "admin")
public class AdminAuthController {
    private final AdminAccountAuthService adminAccountAuthService;

    @PostMapping("/sign-in")
    public Header<JwtTokenResponse> authAdmin(@RequestBody Header<SignInDTO> signInDTOHeader) {
        return Header.ok(adminAccountAuthService.authenticateAdmin(signInDTOHeader.getData()));
    }
}
