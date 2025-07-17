package com.finemeet.authservice.token;

import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.entity.AuthUser;
import com.finemeet.authservice.exception.ResourceAlreadyExistsException;
import com.finemeet.authservice.repository.AuthUserRepository;
import java.util.Optional;

import com.finemeet.authservice.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationTokenGenerator {

    private final TokenManager tokenManager;
    private final AuthUserRepository userCrudRepository;
    private final UserValidator userValidator;

    public void sendVerificationCode(final UserRegistrationRequest request) {
        String email = request.getEmail();

        // throw user already exists exception if email already exists
        userValidator.throwIfEmailExists(email);

        Optional<AuthUser> existingUser = userCrudRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            log.info("User already exists.");
            throw new ResourceAlreadyExistsException("User", "Email");
        }

        String token = tokenManager.generateToken(request);
        log.info("Generated Token for {} is: {}", request, token);
    }
}
