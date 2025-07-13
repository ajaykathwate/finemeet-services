package com.finemeet.authservice;

import com.finemeet.authservice.appconfig.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuthServiceApplication {

	public static void main(String[] args) {
		EnvLoader.load();
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
