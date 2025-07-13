package com.finemeet.authservice.token;

import com.finemeet.authservice.api.UserRegistrationService;
import com.finemeet.authservice.dto.ConfirmTokenRequest;
import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.dto.UserRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenVerifier {

    private final UserRegistrationService userRegistrationService;
    private final TokenManager tokenManager;

    public UserRegistrationResponse confirmEmailByCode(final ConfirmTokenRequest confirmTokenRequest) {
        UserRegistrationRequest userRegistrationRequest = tokenManager.validateToken(confirmTokenRequest);
        return userRegistrationService.register(userRegistrationRequest);
    }

    public void confirmResetPasswordEmailByCode(final ConfirmTokenRequest confirmTokenRequest) {
        tokenManager.validateToken(confirmTokenRequest);
    }
}
