package com.finemeet.userservice;

import com.finemeet.userservice.appconfig.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceApplication {

	public static void main(String[] args) {
		EnvLoader.load();
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
