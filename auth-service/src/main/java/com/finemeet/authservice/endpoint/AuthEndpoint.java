package com.finemeet.authservice.endpoint;

import com.finemeet.authservice.dto.ConfirmTokenRequest;
import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.dto.UserRegistrationResponse;
import com.finemeet.authservice.exception.dto.ApiResponse;
import com.finemeet.authservice.exception.dto.ApiResponseCreator;
import com.finemeet.authservice.service.UserVerificationService;
import com.finemeet.authservice.token.TokenManager;
import com.finemeet.authservice.token.TokenVerifier;
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
    private final UserVerificationService userVerificationService;
    private final TokenVerifier tokenVerifier;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        String email = request.getEmail();

        log.info("Received registration request for user with email = '{}'", email);
        String token = userVerificationService.sendVerificationCode(request);

        ApiResponse apiResponse = apiResponseCreator.buildResponse(
            String.format(
                "A verification email has been sent to %s. If you don't receive it, please check your spam folder or verify your email address.",
                email
            ),
            true,
            HttpStatus.OK,
            token
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<ApiResponse> confirmEmail(@RequestParam("token") String token){
        ConfirmTokenRequest request = new ConfirmTokenRequest(token);

        log.info("Received email confirmation request with token = '{}'", request.token());
        UserRegistrationResponse registrationResponse = tokenVerifier.verityToken(request);
        log.info("Email verification completed for token = '{}'", request.token());

        ApiResponse apiResponse = apiResponseCreator.buildResponse(
            "",
            true,
            HttpStatus.CREATED,
            registrationResponse
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
