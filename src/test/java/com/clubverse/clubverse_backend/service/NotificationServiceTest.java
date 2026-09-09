package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.NotificationResponse;
import com.clubverse.clubverse_backend.entity.Notification;
import com.clubverse.clubverse_backend.entity.NotificationType;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.NotificationRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void createsNotificationForRecipient() {
        User user = user("student@example.com");
        Notification saved = notification(user, "Announcement", false,
                LocalDateTime.now());
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        NotificationResponse response = service().createNotification(
                user, "Announcement", "New announcement", NotificationType.ANNOUNCEMENT);

        assertEquals("Announcement", response.getTitle());
        assertEquals(NotificationType.ANNOUNCEMENT, response.getType());
        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        assertSame(user, captor.getValue().getRecipient());
        assertFalse(captor.getValue().isRead());
    }

    @Test
    void retrievesNotificationsNewestFirstForAuthenticatedUser() {
        User user = user("student@example.com");
        Notification newest = notification(user, "New", false,
                LocalDateTime.of(2026, 9, 9, 12, 0));
        Notification oldest = notification(user, "Old", false,
                LocalDateTime.of(2026, 9, 8, 12, 0));
        when(notificationRepository.findByRecipient_EmailOrderByCreatedAtDesc(user.getEmail()))
                .thenReturn(List.of(newest, oldest));

        List<NotificationResponse> responses = service()
                .getUserNotifications(user.getEmail());

        assertEquals(List.of("New", "Old"),
                responses.stream().map(NotificationResponse::getTitle).toList());
        verify(notificationRepository)
                .findByRecipient_EmailOrderByCreatedAtDesc(user.getEmail());
    }

    @Test
    void returnsUnreadCount() {
        when(notificationRepository.countByRecipient_EmailAndIsReadFalse("student@example.com"))
                .thenReturn(3L);

        assertEquals(3L, service().getUnreadCount("student@example.com"));
    }

    @Test
    void marksOwnedNotificationAsRead() {
        UUID id = UUID.randomUUID();
        User user = user("student@example.com");
        Notification notification = notification(user, "Unread", false, LocalDateTime.now());
        when(notificationRepository.findByNotificationIdAndRecipient_Email(
                id, user.getEmail())).thenReturn(Optional.of(notification));
        when(notificationRepository.save(notification)).thenReturn(notification);

        NotificationResponse response = service().markAsRead(id, user.getEmail());

        assertTrue(response.isRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    void rejectsNotificationOwnedByAnotherUser() {
        UUID id = UUID.randomUUID();
        when(notificationRepository.findByNotificationIdAndRecipient_Email(
                id, "other@example.com")).thenReturn(Optional.empty());

        assertThrows(NotificationNotFoundException.class,
                () -> service().markAsRead(id, "other@example.com"));
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void marksAllUnreadNotificationsAsRead() {
        User user = user("student@example.com");
        Notification first = notification(user, "First", false, LocalDateTime.now());
        Notification second = notification(user, "Second", false, LocalDateTime.now());
        when(notificationRepository.findByRecipient_EmailAndIsReadFalseOrderByCreatedAtDesc(
                user.getEmail())).thenReturn(List.of(first, second));

        assertEquals(2, service().markAllAsRead(user.getEmail()));

        assertTrue(first.isRead());
        assertTrue(second.isRead());
        verify(notificationRepository).saveAll(List.of(first, second));
    }

    private NotificationService service() {
        return new NotificationService(notificationRepository, userRepository);
    }

    private User user(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }

    private Notification notification(User user,
                                     String title,
                                     boolean isRead,
                                     LocalDateTime createdAt) {
        Notification notification = new Notification();
        notification.setRecipient(user);
        notification.setTitle(title);
        notification.setMessage(title + " message");
        notification.setType(NotificationType.ANNOUNCEMENT);
        notification.setRead(isRead);
        notification.setCreatedAt(createdAt);
        return notification;
    }
}