package com.finemeet.authservice.kafka.producer;

import com.finemeet.authservice.kafka.config.KafkaTopicProperties;
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

    public void sendUserRegisteredEvent(String message) {
        String topic = topicProperties.getProducer().getUserRegistered();
        try {
            kafkaTemplate.send(topic, message).get();
            log.info("Sent message to [{}] -> data={}", topic, message);
        } catch (Exception e) {
            log.error("Failed to send message to topic [{}]: {}", topic, e.getMessage(), e);
        }
    }
}
