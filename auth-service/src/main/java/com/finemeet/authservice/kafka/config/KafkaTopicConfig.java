package com.finemeet.authservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    private final KafkaTopicProperties topicProperties;

    public KafkaTopicConfig(KafkaTopicProperties topicProperties) {
        this.topicProperties = topicProperties;
    }

    @Bean
    public NewTopic userRegisteredTopic() {
        return new NewTopic(topicProperties.getProducer().getUserRegistered(), 3, (short) 1);
    }

}
