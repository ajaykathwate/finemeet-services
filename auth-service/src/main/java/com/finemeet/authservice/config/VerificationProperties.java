package com.finemeet.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "verification")
@Getter
@Setter
public class VerificationProperties {

    private String baseUrl;

    public String buildVerificationUrl(String token) {
        return String.format("%s/api/auth/verify?token=%s", baseUrl, token);
    }
}
