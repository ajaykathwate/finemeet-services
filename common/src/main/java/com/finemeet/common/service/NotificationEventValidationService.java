package com.finemeet.common.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventValidationService {

    private final Validator validator;

    public NotificationEventValidationService(Validator validator) {
        this.validator = validator;
    }

    public <T> void validateOrThrow(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new jakarta.validation.ConstraintViolationException("Validation failed for " + object.getClass().getSimpleName(), violations);
        }
    }
}
