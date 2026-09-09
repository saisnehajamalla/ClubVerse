package com.clubverse.clubverse_backend.dto;

import java.time.LocalDate;

public class VolunteerOpportunityResponse {

    private Long id;
    private Long eventId;
    private String eventTitle;
    private String role;
    private String team;
    private String requirements;
    private Integer rewardXp;
    private LocalDate applicationDeadline;
    private Integer totalSlots;
    private Integer filledSlots;
    private boolean active;

    public VolunteerOpportunityResponse(
            Long id,
            Long eventId,
            String eventTitle,
            String role,
            String team,
            String requirements,
            Integer rewardXp,
            LocalDate applicationDeadline,
            Integer totalSlots,
            Integer filledSlots,
            boolean active) {

        this.id = id;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.role = role;
        this.team = team;
        this.requirements = requirements;
        this.rewardXp = rewardXp;
        this.applicationDeadline = applicationDeadline;
        this.totalSlots = totalSlots;
        this.filledSlots = filledSlots;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getRole() {
        return role;
    }

    public String getTeam() {
        return team;
    }

    public String getRequirements() {
        return requirements;
    }

    public Integer getRewardXp() {
        return rewardXp;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public Integer getFilledSlots() {
        return filledSlots;
    }

    public boolean isActive() {
        return active;
    }
}