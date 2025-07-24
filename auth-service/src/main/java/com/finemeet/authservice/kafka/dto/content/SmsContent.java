package com.finemeet.authservice.kafka.dto.content;

import jakarta.validation.constraints.NotBlank;

public class SmsContent extends Content {

    @NotBlank
    private String smsBody;

    // Getters and setters
}

