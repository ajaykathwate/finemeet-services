package com.finemeet.authservice.jwt;

import com.finemeet.authservice.exception.JwtTokenException;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
	private final JwtSignKeyProvider jwtSignKeyProvider;

	@Value("${jwt.expiration}")
	private long validityInMilliseconds;

	@Value("${jwt.refresh.expiration}")
	private long validityRefreshTokenInMilliseconds;

	public String generateToken(final UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	private String generateToken(final Map<String, Object> extraClaims,
	                            final UserDetails userDetails) {
		try {
            Instant now = Instant.now();
            Instant expiry = now.plusMillis(validityInMilliseconds);

            return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(jwtSignKeyProvider.get())
                .compact();
		} catch (Exception exception) {
			log.debug("Jwt token creation error", exception);
			throw new JwtTokenException(exception);
		}
	}

	public String generateRefreshToken(final UserDetails userDetails) {
		try {
            Instant now = Instant.now();
            Instant expiry = now.plusMillis(validityRefreshTokenInMilliseconds);

			return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(jwtSignKeyProvider.get())
                .compact();
		} catch (Exception exception) {
			log.debug("Jwt refresh token creation error", exception);
			throw new JwtTokenException(exception);
		}
	}
}


