package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.NotificationResponse;
import com.clubverse.clubverse_backend.dto.UnreadCountResponse;
import com.clubverse.clubverse_backend.dto.UpdatedNotificationsResponse;
import com.clubverse.clubverse_backend.entity.NotificationType;
import com.clubverse.clubverse_backend.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    private NotificationController controller;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        controller = new NotificationController(notificationService);
        authentication = new UsernamePasswordAuthenticationToken(
                "student@example.com", null, List.of());
    }

    @Test
    void retrievesAuthenticatedUsersNotifications() {
        when(notificationService.getUserNotifications("student@example.com"))
                .thenReturn(List.of());

        assertEquals(200, controller.getNotifications(authentication).getStatusCode().value());
        verify(notificationService).getUserNotifications("student@example.com");
    }

    @Test
    void getsUnreadCountForAuthenticatedUser() {
        when(notificationService.getUnreadCount("student@example.com")).thenReturn(2L);

        UnreadCountResponse response = controller.getUnreadCount(authentication).getBody();

        assertEquals(2L, response.getUnreadCount());
    }

    @Test
    void marksOnlyAuthenticatedUsersNotificationAsRead() {
        UUID id = UUID.randomUUID();
        NotificationResponse notification = new NotificationResponse(
                id, "Title", "Message", NotificationType.COMMENT,
                true, LocalDateTime.now());
        when(notificationService.markAsRead(id, "student@example.com"))
                .thenReturn(notification);

        controller.markAsRead(id, authentication);

        verify(notificationService).markAsRead(id, "student@example.com");
    }

    @Test
    void marksAllAuthenticatedUsersNotificationsAsRead() {
        when(notificationService.markAllAsRead("student@example.com")).thenReturn(3L);

        UpdatedNotificationsResponse response = controller
                .markAllAsRead(authentication).getBody();

        assertEquals(3L, response.getUpdatedCount());
        verify(notificationService).markAllAsRead("student@example.com");
    }
}