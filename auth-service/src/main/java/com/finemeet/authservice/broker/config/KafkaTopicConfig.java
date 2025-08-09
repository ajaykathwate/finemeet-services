package com.finemeet.authservice.broker.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    private final KafkaTopicProperties topicProperties;

    public KafkaTopicConfig(KafkaTopicProperties topicProperties) {
        this.topicProperties = topicProperties;
    }

    @Bean
    public NewTopic notificationsEventsTopic() {
        return TopicBuilder.name(topicProperties.getProducer().getNotifications())
            .partitions(3)
            .replicas(1)
            .build();
    }

}
