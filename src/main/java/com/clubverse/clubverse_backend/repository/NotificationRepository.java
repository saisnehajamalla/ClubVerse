package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByRecipient_EmailOrderByCreatedAtDesc(String email);

    List<Notification> findByRecipient_EmailAndIsReadFalseOrderByCreatedAtDesc(String email);

    long countByRecipient_EmailAndIsReadFalse(String email);

    Optional<Notification> findByNotificationIdAndRecipient_Email(UUID notificationId, String email);
}