package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class RewardResponse {
    private Long id;
    private Long amount;
    private String reason;
    private LocalDateTime createdAt;

    // Constructors
    public RewardResponse() {
    }

    public RewardResponse(Long id, Long amount, String reason, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
