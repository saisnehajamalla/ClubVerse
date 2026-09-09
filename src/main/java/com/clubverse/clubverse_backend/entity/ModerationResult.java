package com.clubverse.clubverse_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "moderation_results")
public class ModerationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModerationDecision decision;

    @Column(nullable = false)
    private String provider;

    @Column(columnDefinition = "TEXT")
    private String reason;

    private Double confidence;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    public ModerationResult() {
    }

    public Long getId() {
        return id;
    }

    public ModerationDecision getDecision() {
        return decision;
    }

    public void setDecision(ModerationDecision decision) {
        this.decision = decision;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}