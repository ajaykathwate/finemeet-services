package com.finemeet.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotificationRecipientValidator.class)
public @interface ValidNotificationRecipient {
    String message() default "Invalid content for channel";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

