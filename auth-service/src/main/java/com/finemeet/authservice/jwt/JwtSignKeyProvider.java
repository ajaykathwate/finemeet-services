package com.finemeet.authservice.jwt;


import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtSignKeyProvider {

    @Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.refresh.secret}")
	private String secretRefreshKey;

    public SecretKey get() {
		log.info("JWT Token: {}", secretKey);
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Key getRefresh() {
		log.info("JWT Refresh Token: {}", secretRefreshKey);
		byte[] keyBytes = Decoders.BASE64.decode(secretRefreshKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

