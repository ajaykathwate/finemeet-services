package com.finemeet.common.notification.content;

import jakarta.validation.constraints.NotBlank;

public class SmsContent extends Content {

    @NotBlank
    private String smsBody;

    // Getters and setters
}

