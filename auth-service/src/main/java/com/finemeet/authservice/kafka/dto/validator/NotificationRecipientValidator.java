package com.finemeet.authservice.kafka.dto.validator;

import com.finemeet.authservice.kafka.dto.NotificationRecipient;
import com.finemeet.authservice.kafka.dto.content.Content;
import com.finemeet.authservice.kafka.dto.content.EmailContent;
import com.finemeet.authservice.kafka.dto.content.PushContent;
import com.finemeet.authservice.kafka.dto.content.SmsContent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotificationRecipientValidator implements ConstraintValidator<ValidNotificationRecipient, NotificationRecipient> {

    @Override
    public boolean isValid(NotificationRecipient recipient, ConstraintValidatorContext context) {
        if (recipient == null || recipient.getChannel() == null || recipient.getContent() == null) {
            return false;
        }

        Content content = recipient.getContent();

        switch (recipient.getChannel()) {
            case EMAIL:
                return content instanceof EmailContent;
            case SMS:
                return content instanceof SmsContent;
            case PUSH:
                return content instanceof PushContent;
            default:
                return false;
        }
    }
}

