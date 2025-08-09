package com.finemeet.notificationservice.broker;

import com.finemeet.common.notification.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumerService {

    private final KafkaTopicProperties kafkaTopicProperties;

    @KafkaListener(
            topics = "#{kafkaTopicProperties.notifications}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, NotificationEvent> consumerRecord, Acknowledgment ack) {
        try {
            log.info(
            "Received message: key={}, value={}, partition={}, offset={}",
            consumerRecord.key(),
            consumerRecord.value(),
            consumerRecord.partition(),
            consumerRecord.offset());

            log.info("Received notification event: {}", consumerRecord.value());

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }
}
