package com.finemeet.authservice.token;

import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.entity.AuthUser;
import com.finemeet.authservice.exception.ResourceAlreadyExistsException;
import com.finemeet.authservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationTokenGenerator {

    private final TokenManager tokenManager;
    private final AuthUserRepository userCrudRepository;

    public String sendEmailVerificationCode(final UserRegistrationRequest request) {
        String email = request.getEmail();

        Optional<AuthUser> existingUser = userCrudRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            log.info("User already exists.");
            throw new ResourceAlreadyExistsException("User", "Email");
        }

        String token = tokenManager.generateToken(request);
        log.info("Generated Token for {} is: {}", request, token);

        return token;
    }
}
