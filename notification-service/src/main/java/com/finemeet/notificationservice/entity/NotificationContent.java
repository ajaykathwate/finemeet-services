package com.finemeet.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notification_contents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class NotificationContent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_delivery_id", nullable = false)
    private NotificationChannelDelivery notificationChannelDelivery;
}
