package com.clubverse.clubverse_backend.dto;

public class ReportRequest {

    private String reportType;
    private Long reportedPostId;
    private Long reportedEventId;
    private Long reportedUserId;
    private String category;
    private String severity;

    public ReportRequest() {}

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Long getReportedPostId() {
        return reportedPostId;
    }

    public void setReportedPostId(Long reportedPostId) {
        this.reportedPostId = reportedPostId;
    }

    public Long getReportedEventId() {
        return reportedEventId;
    }

    public void setReportedEventId(Long reportedEventId) {
        this.reportedEventId = reportedEventId;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}