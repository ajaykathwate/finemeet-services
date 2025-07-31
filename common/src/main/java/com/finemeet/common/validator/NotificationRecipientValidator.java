package com.finemeet.common.validator;

import com.finemeet.common.enums.Channel;
import com.finemeet.common.notification.NotificationRecipient;
import com.finemeet.common.notification.content.Content;
import com.finemeet.common.notification.content.EmailContent;
import com.finemeet.common.notification.content.PushContent;
import com.finemeet.common.notification.content.SmsContent;
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
            case Channel.EMAIL -> {
                return content instanceof EmailContent;
            }
            case Channel.SMS -> {
                return content instanceof SmsContent;
            }
            case Channel.PUSH -> {
                return content instanceof PushContent;
            }
            default -> {
                return false;
            }
        }
    }
}

