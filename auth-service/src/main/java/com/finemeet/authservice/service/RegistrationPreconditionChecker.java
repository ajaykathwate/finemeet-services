package com.finemeet.authservice.service;

import com.finemeet.authservice.exception.ResourceAlreadyExistsException;
import com.finemeet.authservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationPreconditionChecker {

    private final AuthUserRepository userRepository;

    /**
     * Throws if a user with the given email already exists.
     */
    public void checkEmailNotTaken(String email) {
        userRepository.findByEmail(email)
            .ifPresent(u -> {
                throw new ResourceAlreadyExistsException("User", "Email");
            });
    }
}
