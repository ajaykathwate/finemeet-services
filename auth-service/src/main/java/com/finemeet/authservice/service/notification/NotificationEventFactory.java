package com.finemeet.authservice.service.notification;

import com.finemeet.common.enums.Channel;
import com.finemeet.common.enums.NotificationEventType;
import com.finemeet.common.notification.NotificationEvent;
import com.finemeet.common.notification.NotificationRecipient;
import com.finemeet.common.notification.content.EmailContent;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NotificationEventFactory {

    public NotificationEvent userRegisteredEvent(String email, Map<String, Object> templateData) {
        EmailContent emailContent = EmailContent.builder()
                .templateId("email_verification")
                .subject("Welcome to FineMeet! Confirm Your Email to Get Started")
                .templateData(templateData)
                .build();

        NotificationRecipient emailRecipient = NotificationRecipient.builder()
                .channel(Channel.EMAIL)
                .recipient(email)
                .content(emailContent)
                .build();

        return NotificationEvent.builder()
                .recipientId(email)
                .eventType(NotificationEventType.USER_REGISTERED)
                .channels(List.of(Channel.EMAIL))
                .scheduledAt(null)
                .metadata(Map.of())
                .recipients(List.of(emailRecipient))
                .build();
    }
}
