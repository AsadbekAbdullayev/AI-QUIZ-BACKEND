package uz.genesis.aiquest.webserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.repository.UserTalentRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TalentDetailsService implements UserDetailsService {


    private final UserTalentRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdUUID) throws UsernameNotFoundException {
        return userRepository.findById(UUID.fromString(userIdUUID))
                .orElseThrow(() -> new GeneralApiException("user_not_found", ErrorCodes.ERR_NOT_FOUND));
    }
}
