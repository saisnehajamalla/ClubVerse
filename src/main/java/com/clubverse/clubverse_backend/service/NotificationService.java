package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.NotificationResponse;
import com.clubverse.clubverse_backend.entity.Notification;
import com.clubverse.clubverse_backend.entity.NotificationType;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.NotificationRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public NotificationResponse createNotification(User recipient,
                                                   String title,
                                                   String message,
                                                   NotificationType type) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        return toResponse(notificationRepository.save(notification));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUserNotifications(String recipientEmail) {
        return notificationRepository
                .findByRecipient_EmailOrderByCreatedAtDesc(recipientEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadNotifications(String recipientEmail) {
        return notificationRepository
                .findByRecipient_EmailAndIsReadFalseOrderByCreatedAtDesc(recipientEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(String recipientEmail) {
        return notificationRepository.countByRecipient_EmailAndIsReadFalse(recipientEmail);
    }

    @Transactional
    public NotificationResponse markAsRead(UUID notificationId, String recipientEmail) {
        Notification notification = notificationRepository
                .findByNotificationIdAndRecipient_Email(notificationId, recipientEmail)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        notification.setRead(true);
        return toResponse(notificationRepository.save(notification));
    }

    @Transactional
    public long markAllAsRead(String recipientEmail) {
        List<Notification> unreadNotifications = notificationRepository
                .findByRecipient_EmailAndIsReadFalseOrderByCreatedAtDesc(recipientEmail);

        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
        return unreadNotifications.size();
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}