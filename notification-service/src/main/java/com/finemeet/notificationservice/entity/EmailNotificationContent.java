package com.finemeet.notificationservice.entity;

import jakarta.persistence.*;
import java.util.Map;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "email_notification_contents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationContent extends NotificationContent {

    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "template_id", nullable = false, length = 100)
    private String templateId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "template_data", columnDefinition = "jsonb")
    private Map<String, Object> templateData;

    @Override
    public String toString() {
        return "EmailNotificationContent{" +
            "subject='" + subject + '\'' +
            ", email='" + email + '\'' +
            ", templateId='" + templateId + '\'' +
            ", templateData=" + templateData +
            '}';
    }
}
