package com.finemeet.common.notification;


import com.finemeet.common.enums.NotificationChannelEnum;
import com.finemeet.common.notification.content.Content;
import com.finemeet.common.validator.ValidNotificationRecipient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@ValidNotificationRecipient
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRecipient {

    @NotNull
    private NotificationChannelEnum channel;

    @NotNull
    private String recipient;

    @NotNull
    @Valid
    private Content content;

    // Getters and setters
}

