package com.finemeet.notificationservice.service.builder;

import com.finemeet.common.notification.NotificationEvent;
import com.finemeet.common.notification.content.EmailContent;
import com.finemeet.common.notification.content.PushContent;
import com.finemeet.notificationservice.entity.*;
import com.finemeet.notificationservice.enums.DeliveryStatusEnum;
import com.finemeet.notificationservice.enums.NotificationChannelEnum;
import com.finemeet.notificationservice.repository.NotificationChannelDeliveryRepository;
import com.finemeet.notificationservice.repository.NotificationContentRepository;
import com.finemeet.notificationservice.repository.NotificationRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationBuilder {

    private final NotificationRepository notificationRepository;
    private final NotificationChannelDeliveryRepository channelDeliveryRepository;
    private final NotificationContentRepository contentRepository;
    private final NotificationChannelDeliveryRepository notificationChannelDeliveryRepository;

    public Notification build(NotificationEvent request){
        Notification notification =
            Notification.builder()
                .recipientId(request.getRecipientId())
                .channels(request.getChannels().stream().map(
                    channel -> NotificationChannelEnum.valueOf(channel.toString())).toList()
                )
                .eventType(request.getEventType().toString())
                .scheduledAt(request.getScheduledAt())
                .metadata(request.getMetadata())
                .build();

        return notificationRepository.save(notification);
    }

    public List<NotificationChannelDelivery> buildDeliveries(Notification notification, NotificationEvent request) {
        return request.getRecipients().stream()
            .map(recipient -> NotificationChannelDelivery.builder()
                .notification(notification)
                .channel(NotificationChannelEnum.valueOf(recipient.getChannel().toString()))
                .recipientAddress(recipient.getRecipient())
                .status(DeliveryStatusEnum.PENDING)
                .build()
            )
            .map(notificationChannelDeliveryRepository::save)
            .toList();
    }

    public void buildContents(
        Notification notification,
        NotificationEvent request,
        List<NotificationChannelDelivery> deliveries
    ) {
        var deliveryMap = deliveries.stream()
            .collect(Collectors.toMap(
                d -> com.finemeet.common.enums.NotificationChannelEnum.valueOf(d.getChannel().name()),
                d -> d
            ));

        request.getRecipients().stream()
            .map(recipient -> {
                NotificationContent contentEntity = switch (recipient.getChannel()) {
                    case EMAIL -> {
                        EmailContent emailContent = (EmailContent) recipient.getContent();
                        yield EmailNotificationContent.builder()
                            .subject(emailContent.getSubject())
                            .email(recipient.getRecipient())
                            .templateId(emailContent.getTemplateId())
                            .templateData(emailContent.getTemplateData())
                            .build();
                    }
                    case PUSH -> {
                        PushContent pushContent = (PushContent) recipient.getContent();
                        yield PushNotificationContent.builder()
                            .pushTitle(pushContent.getPushTitle())
                            .pushBody(pushContent.getPushBody())
                            .build();
                    }
                    default -> throw new IllegalArgumentException(
                        "Unsupported channel: " + recipient.getChannel()
                    );
                };

                contentEntity.setNotification(notification);
                contentEntity.setNotificationChannelDelivery(
                    Optional.ofNullable(deliveryMap.get(recipient.getChannel()))
                        .orElseThrow(() -> new IllegalArgumentException(
                            "No delivery found for channel: " + recipient.getChannel())
                        )
                );

                return contentEntity;
            })
            .forEach(contentRepository::save);
    }

}
