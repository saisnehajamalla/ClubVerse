package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.ReportRequest;
import com.clubverse.clubverse_backend.dto.ReportResponse;
import com.clubverse.clubverse_backend.entity.Report;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.ReportRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(
            ReportRepository reportRepository,
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
                Report.ReportType.valueOf(
                        request.getReportType().toUpperCase()
                )
        );

        report.setReportedPostId(request.getReportedPostId());
        report.setReportedEventId(request.getReportedEventId());
        report.setReportedUserId(request.getReportedUserId());

        report.setReporterId(reporter.getId());

        report.setCategory(
                Report.Category.valueOf(
                        request.getCategory().toUpperCase()
                )
        );

        report.setSeverity(
                Report.Severity.valueOf(
                        request.getSeverity().toUpperCase()
                )
        );

        report.setStatus(Report.Status.NEW);

        report.setCreatedAt(LocalDateTime.now());

        Report savedReport = reportRepository.save(report);

        return convertToResponse(savedReport);
    }

    public List<ReportResponse> getAllReports(
        String search,
        String category,
        String severity,
        String status,
        String startDate,
        String endDate) {

    List<Report> reports = reportRepository.findAll();

    if (search != null && !search.isBlank()) {

        String keyword = search.toLowerCase();

        reports = reports.stream()
                .filter(report ->
                        report.getReportType().name().toLowerCase().contains(keyword)
                        || report.getCategory().name().toLowerCase().contains(keyword)
                        || report.getSeverity().name().toLowerCase().contains(keyword)
                        || report.getStatus().name().toLowerCase().contains(keyword)
                        || String.valueOf(report.getId()).contains(keyword)
                        || String.valueOf(report.getReporterId()).contains(keyword)
                        || (report.getReportedPostId() != null
                            && String.valueOf(report.getReportedPostId()).contains(keyword))
                        || (report.getReportedEventId() != null
                            && String.valueOf(report.getReportedEventId()).contains(keyword))
                        || (report.getReportedUserId() != null
                            && String.valueOf(report.getReportedUserId()).contains(keyword))
                )
                .toList();
    }

    if (category != null && !category.isBlank()) {
        reports = reports.stream()
                .filter(report ->
                        report.getCategory().name()
                                .equalsIgnoreCase(category))
                .toList();
    }

    if (severity != null && !severity.isBlank()) {
        reports = reports.stream()
                .filter(report ->
                        report.getSeverity().name()
                                .equalsIgnoreCase(severity))
                .toList();
    }

    if (status != null && !status.isBlank()) {
        reports = reports.stream()
                .filter(report ->
                        report.getStatus().name()
                                .equalsIgnoreCase(status))
                .toList();
    }

    if (startDate != null && !startDate.isBlank()) {

        LocalDateTime start =
                LocalDate.parse(startDate).atStartOfDay();

        reports = reports.stream()
                .filter(report ->
                        !report.getCreatedAt().isBefore(start))
                .toList();
    }

    if (endDate != null && !endDate.isBlank()) {

        LocalDateTime end =
                LocalDate.parse(endDate).atTime(23, 59, 59);

        reports = reports.stream()
                .filter(report ->
                        !report.getCreatedAt().isAfter(end))
                .toList();
    }

    return reports.stream()
            .map(this::convertToResponse)
            .toList();
}
    public Page<ReportResponse> getPaginatedReports(
        String search,
        String category,
        String severity,
        String status,
        String startDate,
        String endDate,
        int page,
        int size) {

    List<Report> reports = reportRepository.findAll();

    if (search != null && !search.isBlank()) {
        String keyword = search.toLowerCase();

        reports = reports.stream()
                .filter(report ->
                        report.getReportType().name().toLowerCase().contains(keyword)
                        || report.getCategory().name().toLowerCase().contains(keyword)
                        || report.getSeverity().name().toLowerCase().contains(keyword)
                        || report.getStatus().name().toLowerCase().contains(keyword)
                        || String.valueOf(report.getId()).contains(keyword)
                        || String.valueOf(report.getReporterId()).contains(keyword)
                        || (report.getReportedPostId() != null
                            && String.valueOf(report.getReportedPostId()).contains(keyword))
                        || (report.getReportedEventId() != null
                            && String.valueOf(report.getReportedEventId()).contains(keyword))
                        || (report.getReportedUserId() != null
                            && String.valueOf(report.getReportedUserId()).contains(keyword))
                )
                .toList();
    }

    if (category != null && !category.isBlank()) {
        reports = reports.stream()
                .filter(report ->
                        report.getCategory().name()
                                .equalsIgnoreCase(category))
                .toList();
    }

    if (severity != null && !severity.isBlank()) {
        reports = reports.stream()
                .filter(report ->
                        report.getSeverity().name()
                                .equalsIgnoreCase(severity))
                .toList();
    }

    if (status != null && !status.isBlank()) {
        reports = reports.stream()
                .filter(report ->
                        report.getStatus().name()
                                .equalsIgnoreCase(status))
                .toList();
    }

    if (startDate != null && !startDate.isBlank()) {
        LocalDateTime start =
                LocalDate.parse(startDate).atStartOfDay();

        reports = reports.stream()
                .filter(report ->
                        !report.getCreatedAt().isBefore(start))
                .toList();
    }

    if (endDate != null && !endDate.isBlank()) {
        LocalDateTime end =
                LocalDate.parse(endDate).atTime(23, 59, 59);

        reports = reports.stream()
                .filter(report ->
                        !report.getCreatedAt().isAfter(end))
                .toList();
    }

    Pageable pageable = PageRequest.of(page, size);

    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), reports.size());

    List<ReportResponse> pageContent;

    if (start >= reports.size()) {
        pageContent = List.of();
    } else {
        pageContent = reports.subList(start, end)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    return new PageImpl<>(
            pageContent,
            pageable,
            reports.size()
    );
}

    public ReportResponse updateStatus(
            Long reportId,
            String status) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setStatus(
                Report.Status.valueOf(status.toUpperCase())
        );

        Report updatedReport = reportRepository.save(report);

        return convertToResponse(updatedReport);
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
}