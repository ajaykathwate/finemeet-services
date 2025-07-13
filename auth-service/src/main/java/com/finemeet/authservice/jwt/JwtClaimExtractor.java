package com.finemeet.authservice.jwt;

import com.finemeet.authservice.exception.JwtTokenHasNoUserEmailException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtClaimExtractor {

    private final JwtSignKeyProvider jwtSignKeyProvider;

    public String extractEmail(final String jwtToken){
        String userEmail = extractAllClaims(jwtToken).getSubject();

        if(StringUtils.isEmpty(userEmail)){
            throw new JwtTokenHasNoUserEmailException();
        }
        return userEmail;
    }

    public LocalDateTime extractExpiration(final String jwtToken) {
        Date expiration = extractAllClaims(jwtToken).getExpiration();

        return Instant
            .ofEpochMilli(expiration.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    private Claims extractAllClaims(String jwtToken) {
        return getJwtParser().parseSignedClaims(jwtToken).getPayload();
    }

    private JwtParser getJwtParser() {
        return Jwts
            .parser()
            .verifyWith(jwtSignKeyProvider.get())
            .build();
    }

}

