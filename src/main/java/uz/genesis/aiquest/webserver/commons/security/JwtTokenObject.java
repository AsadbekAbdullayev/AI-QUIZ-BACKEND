package uz.genesis.aiquest.webserver.commons.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import uz.genesis.aiquest.webserver.commons.exception.JwtNotValidException;

import java.security.Key;
import java.util.*;

@Log4j2
@Getter
public class JwtTokenObject {

    private final long expiringMilliseconds;

    private final String secretKey;
    private final String token;

    public JwtTokenObject(long expiringMilliseconds, String secretKey, String token) {
        this.expiringMilliseconds = expiringMilliseconds;
        this.secretKey = secretKey;
        this.token = token;
    }


    public JwtTokenObject(long expiringMilliseconds, String secretKey, String subject, Map<String, Object> claims) {
        this.expiringMilliseconds = expiringMilliseconds;
        this.secretKey = secretKey;
        this.token = generate(subject, claims);
    }


    public String generate(String subject, Map<String, Object> claims) {
        Date expireDate = new Date(System.currentTimeMillis() + expiringMilliseconds);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .setSubject(subject)
                .addClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isExpired() {
        return getClaims(token)
                .getExpiration()
                .before(new Date(System.currentTimeMillis()));
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception exception) {
            log.error("Exception occurred while parsing and validating jwt token : {0}", exception);
            throw new JwtNotValidException("error_during_authorization");
        }
    }


    private Key getSigningKey() {
        var decoded = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decoded);
    }

    public String getSubject() {
        return getClaims(token)
                .getSubject();
    }


    public String getClaimByKey(String emailKey) {
        return getClaims(token).get(emailKey, String.class);
    }
}
