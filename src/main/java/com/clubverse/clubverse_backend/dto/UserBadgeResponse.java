package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class UserBadgeResponse {
    private Long id;
    private BadgeResponse badge;
    private LocalDateTime awardedAt;

    // Constructors
    public UserBadgeResponse() {
    }

    public UserBadgeResponse(Long id, BadgeResponse badge, LocalDateTime awardedAt) {
        this.id = id;
        this.badge = badge;
        this.awardedAt = awardedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BadgeResponse getBadge() {
        return badge;
    }

    public void setBadge(BadgeResponse badge) {
        this.badge = badge;
    }

    public LocalDateTime getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(LocalDateTime awardedAt) {
        this.awardedAt = awardedAt;
    }
}
