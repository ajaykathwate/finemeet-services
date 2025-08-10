package com.finemeet.common.validator;

import com.finemeet.common.enums.NotificationChannelEnum;
import com.finemeet.common.notification.NotificationRecipient;
import com.finemeet.common.notification.content.Content;
import com.finemeet.common.notification.content.EmailContent;
import com.finemeet.common.notification.content.PushContent;
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
            case NotificationChannelEnum.EMAIL -> {
                return content instanceof EmailContent;
            }
            case NotificationChannelEnum.PUSH -> {
                return content instanceof PushContent;
            }
            default -> {
                return false;
            }
        }
    }
}

