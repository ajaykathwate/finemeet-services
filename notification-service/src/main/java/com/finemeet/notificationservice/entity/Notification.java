package com.finemeet.notificationservice.entity;

import com.finemeet.notificationservice.enums.NotificationChannelEnum;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recipient_id", nullable = false)
    private UUID recipientId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "notification_channels", joinColumns = @JoinColumn(name = "notification_id"))
    @Column(name = "channel", nullable = false)
    private List<NotificationChannelEnum> channels;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Type(JsonType.class)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Object metadata;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", recipientId=" + recipientId +
            ", channels=" + channels +
            ", eventType='" + eventType + '\'' +
            ", scheduledAt=" + scheduledAt +
            ", metadata=" + metadata +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
