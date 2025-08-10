package com.finemeet.notificationservice.repository;

import com.finemeet.notificationservice.entity.NotificationChannelDelivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationChannelDeliveryRepository extends JpaRepository<NotificationChannelDelivery, UUID> {}
