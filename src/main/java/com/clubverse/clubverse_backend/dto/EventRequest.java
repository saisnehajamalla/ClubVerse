package com.clubverse.clubverse_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventRequest {

    private String title;
    private String description;
    private String posterUrl;
    private LocalDate eventDate;
    private String venue;
    private LocalTime startTime;
    private LocalTime endTime;
    private String eligibility;
    private Integer maxRegistrations;
    private LocalDate registrationDeadline;
    private boolean volunteerRegistrationEnabled;
    private boolean rewardsEnabled;
    private boolean approvalRequired;
    private Integer rewardXp;
    private boolean featured;
    private boolean freeEntry;
    private String tags;

    public EventRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public Integer getMaxRegistrations() {
        return maxRegistrations;
    }

    public void setMaxRegistrations(Integer maxRegistrations) {
        this.maxRegistrations = maxRegistrations;
    }

    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(LocalDate registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public boolean isVolunteerRegistrationEnabled() {
        return volunteerRegistrationEnabled;
    }

    public void setVolunteerRegistrationEnabled(
            boolean volunteerRegistrationEnabled) {
        this.volunteerRegistrationEnabled = volunteerRegistrationEnabled;
    }

    public boolean isRewardsEnabled() {
        return rewardsEnabled;
    }

    public void setRewardsEnabled(boolean rewardsEnabled) {
        this.rewardsEnabled = rewardsEnabled;
    }

    public boolean isApprovalRequired() {
        return approvalRequired;
    }

    public void setApprovalRequired(boolean approvalRequired) {
        this.approvalRequired = approvalRequired;
    }

    public Integer getRewardXp() {
        return rewardXp;
    }

    public void setRewardXp(Integer rewardXp) {
        this.rewardXp = rewardXp;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isFreeEntry() {
        return freeEntry;
    }

    public void setFreeEntry(boolean freeEntry) {
        this.freeEntry = freeEntry;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}