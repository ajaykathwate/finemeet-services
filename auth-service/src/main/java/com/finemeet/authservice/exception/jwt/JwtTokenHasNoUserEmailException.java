package com.finemeet.authservice.exception.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenHasNoUserEmailException extends AuthenticationException {

	public JwtTokenHasNoUserEmailException() {
		super("User email not found in jwtToken");
	}
}

