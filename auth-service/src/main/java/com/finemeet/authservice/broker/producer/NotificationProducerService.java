package com.finemeet.authservice.broker.producer;

import com.finemeet.authservice.broker.config.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicProperties topicProperties;

    public void publishNotificationsEvent(com.finemeet.common.notification.NotificationEvent notificationEvent) {
        String topic = topicProperties.getProducer().getNotifications();
        try {
            kafkaTemplate.send(topic, notificationEvent.getRecipientId(), notificationEvent.toString());
            log.info("Sent message to [{}] -> data={}", topic, notificationEvent);
        } catch (Exception e) {
            log.error("Failed to send message to topic [{}]: {}", topic, e.getMessage(), e);
            throw new RuntimeException("Failed to send message to topic [%s]: %s".formatted(topic, e.getMessage()), e);
        }
    }
}
