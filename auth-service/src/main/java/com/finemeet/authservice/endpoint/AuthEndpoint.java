package com.finemeet.authservice.endpoint;

import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.exception.dto.ApiResponse;
import com.finemeet.authservice.exception.dto.ApiResponseCreator;
import com.finemeet.authservice.token.TokenManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AuthEndpoint.AUTH_SERVICE_ENDPOINT_URL)
public class AuthEndpoint {

    public static final String AUTH_SERVICE_ENDPOINT_URL = "/api/auth/";

    private final ApiResponseCreator apiResponseCreator;
    private final TokenManager tokenManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        String email = request.getEmail();

        log.info("Received registration request for user with email = '{}'", email);
        String token = tokenManager.generateToken(request);

        ApiResponse apiResponse = apiResponseCreator.buildResponse(
            String.format(
                "Email verification token sent to the user with email = %s%nIf You don't receive an email, " +
                "please check your spam or may be the email address is incorrect",
                email
            ),
            true,
            HttpStatus.OK,
            token
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
