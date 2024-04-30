package uz.genesis.aiquest.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.repository.AdminAccountRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;

@RequiredArgsConstructor
@Service
public class AdminDetailsService implements UserDetailsService {
    private final AdminAccountRepository adminAccountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminAccountRepository.findByEmail(email)
                .orElseThrow(GeneralApiException.throwEx("admin_account_not_found", ErrorCodes.ERR_NOT_FOUND));
    }
}
