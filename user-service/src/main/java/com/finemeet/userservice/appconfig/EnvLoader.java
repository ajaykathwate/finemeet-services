package com.finemeet.userservice.appconfig;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvLoader {
    public static void load() {
        Dotenv dotenv = Dotenv.configure()
            .directory("user-service")
            .filename(".env")
            .load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}

