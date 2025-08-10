package com.finemeet.notificationservice.repository;

import com.finemeet.notificationservice.entity.Notification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {}
