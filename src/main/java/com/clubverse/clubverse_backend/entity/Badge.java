package com.clubverse.clubverse_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "badges")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // e.g., "Event Organizer", "Volunteer Champion"

    @Column(nullable = false)
    private String description;  // e.g., "Organized 5+ events"

    @Column(nullable = false)
    private Long requiredRewardPoints;  // Points required to earn this badge

    @Column(nullable = false)
    private String icon;  // URL or emoji representation

    // Constructors
    public Badge() {
    }

    public Badge(String name, String description, Long requiredRewardPoints, String icon) {
        this.name = name;
        this.description = description;
        this.requiredRewardPoints = requiredRewardPoints;
        this.icon = icon;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequiredRewardPoints() {
        return requiredRewardPoints;
    }

    public void setRequiredRewardPoints(Long requiredRewardPoints) {
        this.requiredRewardPoints = requiredRewardPoints;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
