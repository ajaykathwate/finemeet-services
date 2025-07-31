package com.finemeet.common.notification.content;

import jakarta.validation.constraints.NotBlank;

public class PushContent extends Content {

    @NotBlank
    private String pushTitle;

    @NotBlank
    private String pushBody;

    // Getters and setters
}

