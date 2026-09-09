package com.clubverse.clubverse_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "volunteer_opportunities")
public class VolunteerOpportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String role;

    private String team;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    private Integer rewardXp = 0;

    private LocalDate applicationDeadline;

    private Integer totalSlots;

    private Integer filledSlots = 0;

    private boolean active = true;

    public VolunteerOpportunity() {}

    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Integer getRewardXp() {
        return rewardXp;
    }

    public void setRewardXp(Integer rewardXp) {
        this.rewardXp = rewardXp;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(Integer totalSlots) {
        this.totalSlots = totalSlots;
    }

    public Integer getFilledSlots() {
        return filledSlots;
    }

    public void setFilledSlots(Integer filledSlots) {
        this.filledSlots = filledSlots;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}