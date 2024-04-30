package uz.genesis.aiquest.webserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.genesis.aiquest.webserver.models.dto.TalentProfileDTO;
import uz.genesis.aiquest.webserver.models.dto.UserTalentProfileUpdatePasswordDto;
import uz.genesis.aiquest.webserver.repository.UserTalentRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.clients.LinkedinProfileRapidApiClient;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.projection.TalentCountBySubDirectionProjection;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.mappers.UserTalentProfileMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTalentProfileService {
    private final UserTalentProfileMapper userTalentProfileMapper;
    private final UserTalentRepository userTalentRepository;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final LinkedinProfileRapidApiClient linkedinProfileRapidApiClient;


    public TalentProfileDTO getTalentProfile(@AuthenticationPrincipal UserTalent userTalent) {
        return userTalentProfileMapper.toDto(userTalent);
    }

    @Transactional
    public TalentProfileDTO editTalentProfile(TalentProfileDTO talentProfileDTO) {
        talentProfileDTO.nullifyNotUpdatableFields();
        var talentById = userTalentRepository.findById(talentProfileDTO.getId())
                .orElseThrow();
        userTalentProfileMapper.updateEntityFromDto(talentById, talentProfileDTO);
        if (talentProfileDTO.getRegionId() != null) {
            talentById.setRegionId(talentProfileDTO.getRegionId());
        }
        var updatedEntity = userTalentRepository.save(talentById);
        return userTalentProfileMapper.toDto(updatedEntity);
    }

    public List<TalentCountBySubDirectionProjection> getProjection() {
        return userTalentRepository.getProjection();
    }

    public List<TalentCountBySubDirectionProjection> search(String caption) {
        return userTalentRepository.searchSubDirection(caption.toUpperCase());
    }

    public void updatePassword(UserTalentProfileUpdatePasswordDto dto, UUID uuid) {
        var user = userTalentRepository.findById(uuid).orElseThrow(GeneralApiException.throwEx("user_account_not_found", ErrorCodes.ERR_NOT_FOUND));
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new GeneralApiException("passwords_dont_match", ErrorCodes.ERR_BAD_REQUEST);
        }
        if (!dto.isPasswordAndCopyPasswordMatches()) {
            throw new GeneralApiException("new_passwords_dont_match", ErrorCodes.ERR_BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userTalentRepository.save(user);
    }

    public void deleteAccount(UserTalent userTalent) {
        userTalent.setIsEnabled(false);
        userTalentRepository.save(userTalent);
    }

}
