package com.clubverse.clubverse_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private String posterUrl;
    private LocalDate eventDate;
    private String venue;
    private LocalTime startTime;
    private LocalTime endTime;
    private String eligibility;
    private Integer maxRegistrations;
    private Integer registeredCount;
    private LocalDate registrationDeadline;
    private boolean volunteerRegistrationEnabled;
    private boolean rewardsEnabled;
    private boolean approvalRequired;
    private Integer rewardXp;
    private boolean featured;
    private boolean freeEntry;
    private String tags;
    private Long organizerId;
    private String status;
    private LocalDateTime createdAt;

    public EventResponse() {}

    public EventResponse(
            Long id,
            String title,
            String description,
            String posterUrl,
            LocalDate eventDate,
            String venue,
            LocalTime startTime,
            LocalTime endTime,
            String eligibility,
            Integer maxRegistrations,
            Integer registeredCount,
            LocalDate registrationDeadline,
            boolean volunteerRegistrationEnabled,
            boolean rewardsEnabled,
            boolean approvalRequired,
            Integer rewardXp,
            boolean featured,
            boolean freeEntry,
            String tags,
            Long organizerId,
            String status,
            LocalDateTime createdAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.eventDate = eventDate;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eligibility = eligibility;
        this.maxRegistrations = maxRegistrations;
        this.registeredCount = registeredCount;
        this.registrationDeadline = registrationDeadline;
        this.volunteerRegistrationEnabled = volunteerRegistrationEnabled;
        this.rewardsEnabled = rewardsEnabled;
        this.approvalRequired = approvalRequired;
        this.rewardXp = rewardXp;
        this.featured = featured;
        this.freeEntry = freeEntry;
        this.tags = tags;
        this.organizerId = organizerId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getEligibility() {
        return eligibility;
    }

    public Integer getMaxRegistrations() {
        return maxRegistrations;
    }

    public Integer getRegisteredCount() {
        return registeredCount;
    }

    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public boolean isVolunteerRegistrationEnabled() {
        return volunteerRegistrationEnabled;
    }

    public boolean isRewardsEnabled() {
        return rewardsEnabled;
    }

    public boolean isApprovalRequired() {
        return approvalRequired;
    }

    public Integer getRewardXp() {
        return rewardXp;
    }

    public boolean isFeatured() {
        return featured;
    }

    public boolean isFreeEntry() {
        return freeEntry;
    }

    public String getTags() {
        return tags;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}