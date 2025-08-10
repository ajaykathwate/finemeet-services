package com.finemeet.notificationservice.service;

import com.finemeet.common.notification.NotificationEvent;
import com.finemeet.notificationservice.service.builder.NotificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationBuilder notificationBuilder;

    public void processNotification(NotificationEvent request) {
        log.info("Starting notification processing for event: {}", request);

        try {
            var notification = notificationBuilder.build(request);
            log.info("Notification entity built: {}", notification);

            var deliveries = notificationBuilder.buildDeliveries(notification, request);
            log.info("Deliveries built: {}", deliveries);

            notificationBuilder.buildContents(notification, request, deliveries);
            log.info("Contents built successfully for notification: {}", notification.getId());

            // TODO: Send to NotificationEngine

        } catch (IllegalArgumentException e) {
            log.warn("Invalid notification event: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while processing notification", e);
            throw e;
        }
    }

}
