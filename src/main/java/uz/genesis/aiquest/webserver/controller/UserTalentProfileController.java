package uz.genesis.aiquest.webserver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.models.dto.TalentLinkedinUrlDTO;
import uz.genesis.aiquest.webserver.models.dto.TalentProfileDTO;
import uz.genesis.aiquest.webserver.models.dto.UserTalentProfileUpdatePasswordDto;
import uz.genesis.aiquest.webserver.service.UserTalentProfileService;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.TM_BASE_TALENT_URL + "/profile")
@SecurityRequirement(name = "talent")
public class UserTalentProfileController {
    private final UserTalentProfileService userTalentProfileService;

    @GetMapping(value = "/me")
    public Header<TalentProfileDTO> getTalentProfile(@AuthenticationPrincipal UserTalent userTalent) {
        return Header.ok(userTalentProfileService.getTalentProfile(userTalent));
    }

    @PutMapping(value = "/edit")
    public Header<TalentProfileDTO> editProfile(@RequestBody Header<TalentProfileDTO> talentProfileDTOHeader) {
        return Header.ok(userTalentProfileService.editTalentProfile(talentProfileDTOHeader.getData()));
    }

    @GetMapping(value = "/delete")
    public void deleteProfile(@AuthenticationPrincipal UserTalent userTalent) {
        userTalentProfileService.deleteAccount(userTalent);
    }

    @PatchMapping("/change-password")
    public Header<?> updatePassword(@AuthenticationPrincipal UserTalent userTalent, @RequestBody @Valid Header<UserTalentProfileUpdatePasswordDto> dto) {
        userTalentProfileService.updatePassword(dto.getData(), userTalent.getId());
        return Header.ok();
    }



}
