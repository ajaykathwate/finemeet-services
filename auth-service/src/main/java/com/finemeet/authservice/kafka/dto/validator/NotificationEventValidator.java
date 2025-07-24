package com.finemeet.authservice.kafka.dto.validator;

import com.finemeet.authservice.kafka.dto.Channel;
import com.finemeet.authservice.kafka.dto.NotificationEvent;
import com.finemeet.authservice.kafka.dto.NotificationRecipient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NotificationEventValidator implements ConstraintValidator<ValidNotificationEvent, NotificationEvent> {

    @Override
    public boolean isValid(NotificationEvent event, ConstraintValidatorContext context) {
        if (event == null) return true;

        List<Channel> channels = event.getChannels();
        List<NotificationRecipient> recipients = event.getRecipients();

        if (channels == null || recipients == null) return false;

        Set<Channel> recipientChannels = recipients.stream()
            .map(NotificationRecipient::getChannel)
            .collect(Collectors.toSet());

        for (Channel channel : channels) {
            if (!recipientChannels.contains(channel)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Missing recipient for channel: " + channel)
                       .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
