package com.finemeet.authservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Null check
        if (password == null) {
            sendValidationError(context, "Password cannot be null");
            return false;
        }

        // Length check
        if (password.length() < 8 || password.length() > 16) {
            sendValidationError(context, "Password must be 8-16 characters");
            return false;
        }

        // Lowercase letter check
        if (!password.matches(".*[a-z].*")) {
            sendValidationError(context, "Password must contain at least 1 lowercase letter");
            return false;
        }

        // Uppercase letter check
        if (!password.matches(".*[A-Z].*")) {
            sendValidationError(context, "Password must contain at least 1 uppercase letter");
            return false;
        }

        // Digit check
        if (!password.matches(".*\\d.*")) {
            sendValidationError(context, "Password must contain at least 1 number");
            return false;
        }

        // Special character check
        if (!password.matches(".*[@$!%*?&].*")) {
            sendValidationError(context, "Password must contain at least 1 special character (@$!%*?&)");
            return false;
        }

        // Check for invalid characters
        if (!password.matches("[A-Za-z\\d@$!%*?&]+")) {
            sendValidationError(context, "Password contains invalid characters. Only letters, numbers, and @$!%*?& are allowed");
            return false;
        }

        return true;
    }

    private void sendValidationError(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }

}


