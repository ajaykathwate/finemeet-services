package com.finemeet.common.notification;

import com.finemeet.common.enums.Channel;
import com.finemeet.common.enums.NotificationEventType;
import com.finemeet.common.validator.ValidNotificationEvent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ValidNotificationEvent
public class NotificationEvent {
    @NotNull
    private String recipientId;

    @NotEmpty
    private List<Channel> channels;

    @NotNull
    private NotificationEventType eventType;

    private LocalDateTime scheduledAt;

    private Map<String, Object> metadata;

    @NotEmpty
    @Valid
    private List<NotificationRecipient> recipients;
}
