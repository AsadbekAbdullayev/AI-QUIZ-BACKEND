package uz.genesis.aiquest.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.mappers.AdminAccountMapper;
import uz.genesis.aiquest.admin.repository.AdminAccountRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.admin.dto.AdminAccountDto;
import uz.genesis.aiquest.admin.dto.AdminAccountUpdatePasswordDto;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.entity.AdminAccount;
import uz.genesis.aiquest.webserver.models.enums.EAccountRole;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

    private final AdminAccountRepository adminAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminAccountMapper adminAccountMapper;
    private static final String superAdminEmail = "super@gmail.com";
    private static final String superAdminPassword = "12345";
    private static final String superAdminName = "Super";
    private static final String superAdminLastName = "Admin";

    public void insertSuperAdmin() {
        if (adminAccountRepository.existsByEmail(superAdminEmail)) {
            return;
        }

        var adminAccount = AdminAccount.builder()
                .accountRole(EAccountRole.SUPER_ADMIN)
                .email(superAdminEmail)
                .password(passwordEncoder.encode(superAdminPassword))
                .firstName(superAdminName)
                .lastName(superAdminLastName)
                .isActive(true)
                .build();
        adminAccountRepository.save(adminAccount);
    }



    public void updatePassword(AdminAccountUpdatePasswordDto dto, UUID uuid) {
        var admin = adminAccountRepository.findById(uuid).orElseThrow(GeneralApiException.throwEx("admin_account_not_found", ErrorCodes.ERR_NOT_FOUND));
        if (!passwordEncoder.matches(dto.getOldPassword(), admin.getPassword())) {
            throw new GeneralApiException("passwords_dont_match", ErrorCodes.ERR_BAD_REQUEST);
        }
        admin.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        adminAccountRepository.save(admin);
    }

    public AdminAccountDto updateInfo(AdminAccountDto adminAccountUpdateInfo, UUID uuid) {
        var admin = adminAccountRepository.findById(uuid).orElseThrow(GeneralApiException.throwEx("admin_account_not_found", ErrorCodes.ERR_NOT_FOUND));
        adminAccountMapper.updateEntityFromDto(admin, adminAccountUpdateInfo);
        var updatedAdminAccount = adminAccountRepository.save(admin);
        return adminAccountMapper.toDto(updatedAdminAccount);
    }

    public AdminAccountDto save(AdminAccountDto adminAccountDto) {
        AdminAccount adminAccount = adminAccountMapper.toEntity(adminAccountDto);
        if (adminAccountRepository.existsByEmail(adminAccountDto.getEmail()))
            throw new GeneralApiException("admin_account_already_exists", ErrorCodes.ERR_BAD_REQUEST);
        var savedAdmin = adminAccountRepository.save(adminAccount);
        return adminAccountMapper.toDto(savedAdmin);
    }

    public void delete(UUID id) {
        adminAccountRepository.deleteById(id);
    }

    public List<AdminAccountDto> getAll() {
        List<AdminAccount> all = adminAccountRepository.findAll();
        return adminAccountMapper.toDtoList(all);
    }

    public AdminAccountDto getOneById(UUID id) {
        var admin = adminAccountRepository.findById(id).orElseThrow(GeneralApiException.throwEx("admin_account_not_found", ErrorCodes.ERR_NOT_FOUND));
        return adminAccountMapper.toDto(admin);
    }

    public AdminAccountDto getMe(AdminAccount adminAccount) {
        return adminAccountMapper.toDto(adminAccount);
    }
}
