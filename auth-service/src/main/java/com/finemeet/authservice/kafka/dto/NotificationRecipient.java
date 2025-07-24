package com.finemeet.authservice.kafka.dto;

import com.finemeet.authservice.kafka.dto.content.Content;
import com.finemeet.authservice.kafka.dto.validator.ValidNotificationRecipient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@ValidNotificationRecipient
public class NotificationRecipient {

    @NotNull
    private Channel channel;

    @NotNull
    private String recipient;

    @NotNull
    @Valid
    private Content content;

    // Getters and setters
}

