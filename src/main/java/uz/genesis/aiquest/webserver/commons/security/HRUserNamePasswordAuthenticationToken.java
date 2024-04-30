package uz.genesis.aiquest.webserver.commons.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class HRUserNamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public HRUserNamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
