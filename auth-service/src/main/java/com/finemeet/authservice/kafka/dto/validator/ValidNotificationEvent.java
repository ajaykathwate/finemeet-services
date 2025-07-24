package com.finemeet.authservice.kafka.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotificationEventValidator.class)
public @interface ValidNotificationEvent {
    String message() default "Each channel must have a corresponding recipient";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

