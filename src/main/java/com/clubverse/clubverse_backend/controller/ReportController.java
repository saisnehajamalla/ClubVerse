package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.ReportRequest;
import com.clubverse.clubverse_backend.dto.ReportResponse;
import com.clubverse.clubverse_backend.entity.Report;
import com.clubverse.clubverse_backend.service.ReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(
            @RequestBody ReportRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        ReportResponse response =
                reportService.createReport(request, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {

        return ResponseEntity.ok(
                reportService.getAllReports()
        );
    }

    @PutMapping("/{reportId}/status")
    public ResponseEntity<ReportResponse> updateStatus(
            @PathVariable Long reportId,
            @RequestParam String status) {

        ReportResponse response =
                reportService.updateStatus(reportId, status);

        return ResponseEntity.ok(response);
    }
}