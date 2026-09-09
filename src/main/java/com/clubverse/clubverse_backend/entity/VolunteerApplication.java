package com.clubverse.clubverse_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "volunteer_applications",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"opportunity_id", "user_id"}
        )
    }
)
public class VolunteerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "opportunity_id", nullable = false)
    private VolunteerOpportunity opportunity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String applicationMessage;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime appliedAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public VolunteerApplication() {}

    public Long getId() {
        return id;
    }

    public VolunteerOpportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(VolunteerOpportunity opportunity) {
        this.opportunity = opportunity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApplicationMessage() {
        return applicationMessage;
    }

    public void setApplicationMessage(String applicationMessage) {
        this.applicationMessage = applicationMessage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}