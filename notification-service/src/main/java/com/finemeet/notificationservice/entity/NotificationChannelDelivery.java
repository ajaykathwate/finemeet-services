package com.finemeet.notificationservice.entity;

import com.finemeet.notificationservice.enums.DeliveryStatusEnum;
import com.finemeet.notificationservice.enums.NotificationChannelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification_channel_deliveries",
       indexes = {
           @Index(name = "idx_delivery_channel", columnList = "channel"),
           @Index(name = "idx_delivery_status", columnList = "status")
       })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationChannelDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 20)
    private NotificationChannelEnum channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryStatusEnum status;

    @Column(name = "recipient_address", nullable = false, length = 255)
    private String recipientAddress;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

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
        return "NotificationChannelDelivery{" +
            "id=" + id +
            ", notification=" + notification +
            ", channel=" + channel +
            ", status=" + status +
            ", recipientAddress='" + recipientAddress + '\'' +
            ", errorMessage='" + errorMessage + '\'' +
            ", retryCount=" + retryCount +
            ", sentAt=" + sentAt +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}

