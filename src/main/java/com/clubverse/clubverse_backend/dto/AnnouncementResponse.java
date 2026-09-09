package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class AnnouncementResponse {

    private Long id;
    private String title;
    private String messageBody;
    private String mediaUrl;
    private String targetAudience;
    private Long eventId;
    private LocalDateTime scheduledAt;
    private String status;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnnouncementResponse(
            Long id,
            String title,
            String messageBody,
            String mediaUrl,
            String targetAudience,
            Long eventId,
            LocalDateTime scheduledAt,
            String status,
            Long createdBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.title = title;
        this.messageBody = messageBody;
        this.mediaUrl = mediaUrl;
        this.targetAudience = targetAudience;
        this.eventId = eventId;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public Long getEventId() {
        return eventId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public String getStatus() {
        return status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}