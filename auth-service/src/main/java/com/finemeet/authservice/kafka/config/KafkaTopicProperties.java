package com.finemeet.authservice.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "app.kafka.topics")
public class KafkaTopicProperties {

    private Producer producer;
    private Consumer consumer;

    @Getter
    @Setter
    public static class Producer {
        private String userRegistered;
    }

    @Getter
    @Setter
    public static class Consumer {
        private String userRegistered;
    }
}

