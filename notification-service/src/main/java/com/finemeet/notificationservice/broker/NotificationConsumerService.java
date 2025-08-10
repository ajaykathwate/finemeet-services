package com.finemeet.notificationservice.broker;

import com.finemeet.common.notification.NotificationEvent;
import com.finemeet.notificationservice.service.NotificationService;
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
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "#{kafkaTopicProperties.notifications}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, NotificationEvent> consumerRecord, Acknowledgment ack) {
        log.debug("Kafka listener triggered for topic '{}'", kafkaTopicProperties.getNotifications());

        try {
            log.info(
                "Received Kafka message: key={}, partition={}, offset={}, value={}",
                consumerRecord.key(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                consumerRecord.value()
            );

            var notificationEvent = consumerRecord.value();
            notificationService.processNotification(notificationEvent);

            ack.acknowledge();
            log.debug("Kafka message acknowledged: key={}, partition={}, offset={}",
                      consumerRecord.key(),
                      consumerRecord.partition(),
                      consumerRecord.offset());

        } catch (Exception e) {
            log.error(
                "Error processing Kafka message: key={}, partition={}, offset={}, error={}",
                consumerRecord.key(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                e.getMessage(),
                e
            );
        }
    }
}
