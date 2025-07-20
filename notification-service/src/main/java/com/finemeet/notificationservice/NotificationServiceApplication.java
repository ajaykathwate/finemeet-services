package com.finemeet.notificationservice;

import com.finemeet.notificationservice.config.appconfig.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NotificationServiceApplication {

	public static void main(String[] args) {
		EnvLoader.load();
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
