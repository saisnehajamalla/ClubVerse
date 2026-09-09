package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class ReportResponse {

    private Long id;
    private String reportType;
    private Long reportedPostId;
    private Long reportedEventId;
    private Long reportedUserId;
    private Long reporterId;
    private String category;
    private String severity;
    private String status;
    private LocalDateTime createdAt;

    public ReportResponse() {}

    public ReportResponse(
            Long id,
            String reportType,
            Long reportedPostId,
            Long reportedEventId,
            Long reportedUserId,
            Long reporterId,
            String category,
            String severity,
            String status,
            LocalDateTime createdAt) {

        this.id = id;
        this.reportType = reportType;
        this.reportedPostId = reportedPostId;
        this.reportedEventId = reportedEventId;
        this.reportedUserId = reportedUserId;
        this.reporterId = reporterId;
        this.category = category;
        this.severity = severity;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getReportType() {
        return reportType;
    }

    public Long getReportedPostId() {
        return reportedPostId;
    }

    public Long getReportedEventId() {
        return reportedEventId;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getCategory() {
        return category;
    }

    public String getSeverity() {
        return severity;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}