package uz.genesis.aiquest.admin.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AdminAccountAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public AdminAccountAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AdminAccountAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
