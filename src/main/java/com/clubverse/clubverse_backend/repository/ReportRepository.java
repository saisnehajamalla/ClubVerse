package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByCategory(Report.Category category);

    List<Report> findBySeverity(Report.Severity severity);

    List<Report> findByStatus(Report.Status status);

    List<Report> findByReportType(Report.ReportType reportType);

    List<Report> findByReportedPostId(Long reportedPostId);

    List<Report> findByReportedEventId(Long reportedEventId);

    List<Report> findByReportedUserId(Long reportedUserId);
    
    List<Report> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}