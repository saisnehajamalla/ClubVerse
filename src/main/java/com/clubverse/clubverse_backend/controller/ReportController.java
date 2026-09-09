package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.ReportRequest;
import com.clubverse.clubverse_backend.dto.ReportResponse;
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
    public ResponseEntity<List<ReportResponse>> getAllReports(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String severity,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate) {

    return ResponseEntity.ok(
            reportService.getAllReports(
                    search,
                    category,
                    severity,
                    status,
                    startDate,
                    endDate
            )
    );
    }
    @GetMapping("/page")
    public ResponseEntity<?> getPaginatedReports(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String severity,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    return ResponseEntity.ok(
            reportService.getPaginatedReports(
                    search,
                    category,
                    severity,
                    status,
                    startDate,
                    endDate,
                    page,
                    size
            )
    );
    }
}