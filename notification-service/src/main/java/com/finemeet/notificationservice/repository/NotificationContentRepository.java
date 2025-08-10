package com.finemeet.notificationservice.repository;

import com.finemeet.notificationservice.entity.NotificationContent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationContentRepository extends JpaRepository<NotificationContent, UUID> {}
