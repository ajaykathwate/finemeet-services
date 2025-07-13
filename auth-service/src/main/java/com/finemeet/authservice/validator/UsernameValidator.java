package com.finemeet.authservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z][a-z0-9]*(?:@[a-z0-9]+)*$");

    // blocked usernames that user can't use
    private static final Set<String> BLOCKED_USERNAMES = Set.of(
        "admin",
        "support",
        "root",
        "system",
        "moderator",
        "supervisor",
        "manager",
        "attendee",
        "organizer"
    );

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context){
        if (username == null) return false;
        if(username.length() < 3 || username.length() > 16) return false;
        if(!USERNAME_PATTERN.matcher(username).matches()) return false;

        if(username.startsWith("_") || username.endsWith("_")) return false;
        if(username.contains("__")) return false;

        // 🚫 Blocked username - override message
        if(BLOCKED_USERNAMES.contains(username.toLowerCase())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username '" + username + "' is not allowed to use.")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }

}


