package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class RegistrationResponse {

    private Long id;
    private Long eventId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String status;
    private LocalDateTime registeredAt;

    public RegistrationResponse() {}

    public RegistrationResponse(
            Long id,
            Long eventId,
            Long userId,
            String userName,
            String userEmail,
            String status,
            LocalDateTime registeredAt) {

        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
