package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.NotificationResponse;
import com.clubverse.clubverse_backend.dto.UnreadCountResponse;
import com.clubverse.clubverse_backend.dto.UpdatedNotificationsResponse;
import com.clubverse.clubverse_backend.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            Authentication authentication) {
        return ResponseEntity.ok(
                notificationService.getUserNotifications(authentication.getName())
        );
    }

    @GetMapping("/unread-count")
    public ResponseEntity<UnreadCountResponse> getUnreadCount(
            Authentication authentication) {
        return ResponseEntity.ok(
                new UnreadCountResponse(
                        notificationService.getUnreadCount(authentication.getName())
                )
        );
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponse> markAsRead(
            @PathVariable UUID notificationId,
            Authentication authentication) {
        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId, authentication.getName())
        );
    }

    @PatchMapping("/read-all")
    public ResponseEntity<UpdatedNotificationsResponse> markAllAsRead(
            Authentication authentication) {
        return ResponseEntity.ok(
                new UpdatedNotificationsResponse(
                        notificationService.markAllAsRead(authentication.getName())
                )
        );
    }
}