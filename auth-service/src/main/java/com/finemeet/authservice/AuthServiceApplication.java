package com.finemeet.authservice;

import com.finemeet.authservice.config.appconfig.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {
    "com.finemeet.authservice",
    "com.finemeet.common"
})
public class AuthServiceApplication {

	public static void main(String[] args) {
		EnvLoader.load();
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
