package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.ReportRequest;
import com.clubverse.clubverse_backend.dto.ReportResponse;
import com.clubverse.clubverse_backend.entity.Report;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.ReportRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public ReportResponse createReport(
            ReportRequest request,
            String email) {

        User reporter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();

        report.setReportType(
                Report.ReportType.valueOf(request.getReportType().toUpperCase())
        );

        report.setReportedPostId(request.getReportedPostId());
        report.setReportedEventId(request.getReportedEventId());
        report.setReportedUserId(request.getReportedUserId());

        report.setReporterId(reporter.getId());

        report.setCategory(
                Report.Category.valueOf(request.getCategory().toUpperCase())
        );

        report.setSeverity(
                Report.Severity.valueOf(request.getSeverity().toUpperCase())
        );

        report.setStatus(Report.Status.NEW);

        report.setCreatedAt(LocalDateTime.now());

        Report savedReport = reportRepository.save(report);

        return convertToResponse(savedReport);
    }

    public List<ReportResponse> getAllReports() {

        return reportRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ReportResponse convertToResponse(Report report) {

        return new ReportResponse(
                report.getId(),
                report.getReportType().name(),
                report.getReportedPostId(),
                report.getReportedEventId(),
                report.getReportedUserId(),
                report.getReporterId(),
                report.getCategory().name(),
                report.getSeverity().name(),
                report.getStatus().name(),
                report.getCreatedAt()
        );
    }
    public ReportResponse updateStatus(Long reportId, String status) {

    Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("Report not found"));

    report.setStatus(
            Report.Status.valueOf(status.toUpperCase())
    );

    Report updatedReport = reportRepository.save(report);

    return convertToResponse(updatedReport);
   }
}