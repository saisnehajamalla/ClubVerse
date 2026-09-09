package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class VolunteerApplicationResponse {

    private Long id;
    private Long opportunityId;
    private Long eventId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String role;
    private String team;
    private String applicationMessage;
    private String status;
    private LocalDateTime appliedAt;

    public VolunteerApplicationResponse(
            Long id,
            Long opportunityId,
            Long eventId,
            Long userId,
            String userName,
            String userEmail,
            String role,
            String team,
            String applicationMessage,
            String status,
            LocalDateTime appliedAt) {

        this.id = id;
        this.opportunityId = opportunityId;
        this.eventId = eventId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.role = role;
        this.team = team;
        this.applicationMessage = applicationMessage;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getOpportunityId() {
        return opportunityId;
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

    public String getRole() {
        return role;
    }

    public String getTeam() {
        return team;
    }

    public String getApplicationMessage() {
        return applicationMessage;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}