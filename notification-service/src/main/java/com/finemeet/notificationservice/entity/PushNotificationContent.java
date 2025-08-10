package com.finemeet.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "push_notification_contents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationContent extends NotificationContent {

    @Column(name = "device_token", nullable = false, length = 255)
    private String deviceToken;

    @Column(name = "push_title", nullable = false, length = 255)
    private String pushTitle;

    @Column(name = "push_body", nullable = false, length = 500)
    private String pushBody;

    @Override
    public String toString() {
        return "PushNotificationContent{" +
            "deviceToken='" + deviceToken + '\'' +
            ", pushTitle='" + pushTitle + '\'' +
            ", pushBody='" + pushBody + '\'' +
            '}';
    }
}
