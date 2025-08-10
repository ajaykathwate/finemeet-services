package com.finemeet.common.notification;

import com.finemeet.common.enums.NotificationChannelEnum;
import com.finemeet.common.enums.NotificationEventType;
import com.finemeet.common.validator.ValidNotificationEvent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    private UUID recipientId;

    @NotEmpty
    private List<NotificationChannelEnum> channels;

    @NotNull
    private NotificationEventType eventType;

    private LocalDateTime scheduledAt;

    private Map<String, Object> metadata;

    @NotEmpty
    @Valid
    private List<NotificationRecipient> recipients;
}
