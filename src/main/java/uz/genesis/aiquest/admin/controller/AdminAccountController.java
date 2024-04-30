package uz.genesis.aiquest.admin.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.admin.dto.AdminAccountDto;
import uz.genesis.aiquest.admin.dto.AdminAccountUpdatePasswordDto;
import uz.genesis.aiquest.admin.service.AdminAccountService;
import uz.genesis.aiquest.webserver.models.entity.AdminAccount;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.TM_BASE_ADMIN_URL + "/accounts")
@SecurityRequirement(name = "admin")
public class AdminAccountController {
    private final AdminAccountService adminAccountService;

    @PatchMapping("/change-password")
    public Header<?> updatePassword(@AuthenticationPrincipal AdminAccount adminAccount, @RequestBody Header<AdminAccountUpdatePasswordDto> dto){
        adminAccountService.updatePassword(dto.getData(),adminAccount.getId());
        return Header.ok();
    }

    @GetMapping(value = "/me")
    public Header<AdminAccountDto> getMe(@AuthenticationPrincipal AdminAccount adminAccount){
        return Header.ok(adminAccountService.getMe(adminAccount));
    }

    @PostMapping
    public Header<AdminAccountDto> addAdmin(@RequestBody @Valid Header<AdminAccountDto> adminAccountDto){
        return Header.ok(adminAccountService.save(adminAccountDto.getData()));
    }

    @PutMapping("/{id}")
    public Header<AdminAccountDto> updateInfo(@PathVariable UUID id, @RequestBody Header<AdminAccountDto> adminAccountUpdateInfo){
        var updatedAdmin = adminAccountService.updateInfo(adminAccountUpdateInfo.getData(), id);
        return Header.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public Header<?> delete(@PathVariable UUID id){
        adminAccountService.delete(id);
        return Header.ok();
    }

    @GetMapping("/all")
    public Header<List<AdminAccountDto>> getAll(){
        return Header.ok(adminAccountService.getAll());
    }

    @GetMapping("/{id}")
    public Header<AdminAccountDto> getOneById(@PathVariable UUID id){
        return Header.ok(adminAccountService.getOneById(id));
    }

}
