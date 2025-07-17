package com.finemeet.authservice.validator;

import com.finemeet.authservice.exception.ResourceAlreadyExistsException;
import com.finemeet.authservice.exception.ResourceNotFoundException;
import com.finemeet.authservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final AuthUserRepository authUserRepository;

    public void throwIfEmailExists(String email) {
        if (authUserRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "Email");
        }
    }

    public void throwIfEmailNotExists(String email) {
        if (authUserRepository.findByEmail(email).isEmpty()) {
            throw new ResourceNotFoundException("User", "email", email);
        }
    }
}

