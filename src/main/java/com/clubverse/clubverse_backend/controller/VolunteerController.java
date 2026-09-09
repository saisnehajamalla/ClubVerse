package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.VolunteerApplicationRequest;
import com.clubverse.clubverse_backend.dto.VolunteerApplicationResponse;
import com.clubverse.clubverse_backend.dto.VolunteerOpportunityRequest;
import com.clubverse.clubverse_backend.dto.VolunteerOpportunityResponse;
import com.clubverse.clubverse_backend.service.VolunteerService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    // Create a volunteer opportunity
    @PostMapping("/opportunities")
    public ResponseEntity<VolunteerOpportunityResponse>
    createOpportunity(
            @RequestBody VolunteerOpportunityRequest request) {

        return ResponseEntity.ok(
                volunteerService.createOpportunity(request)
        );
    }

    // Get all active volunteer opportunities
    @GetMapping("/opportunities")
    public ResponseEntity<List<VolunteerOpportunityResponse>>
    getAllOpportunities() {

        return ResponseEntity.ok(
                volunteerService.getAllOpportunities()
        );
    }

    // Get opportunities for a particular event
    @GetMapping("/events/{eventId}/opportunities")
    public ResponseEntity<List<VolunteerOpportunityResponse>>
    getEventOpportunities(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                volunteerService.getEventOpportunities(eventId)
        );
    }

    // Student applies for a volunteer opportunity
    @PostMapping("/opportunities/{opportunityId}/apply")
    public ResponseEntity<VolunteerApplicationResponse>
    apply(
            @PathVariable Long opportunityId,
            @RequestBody VolunteerApplicationRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                volunteerService.apply(
                        opportunityId,
                        request,
                        email
                )
        );
    }

    // Get applications for an opportunity
    @GetMapping("/opportunities/{opportunityId}/applications")
    public ResponseEntity<List<VolunteerApplicationResponse>>
    getOpportunityApplications(
            @PathVariable Long opportunityId) {

        return ResponseEntity.ok(
                volunteerService
                        .getOpportunityApplications(opportunityId)
        );
    }

    // Get logged-in student's applications
    @GetMapping("/my-applications")
    public ResponseEntity<List<VolunteerApplicationResponse>>
    getMyApplications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                volunteerService.getMyApplications(email)
        );
    }

    // Accept or reject an application
    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<VolunteerApplicationResponse>
    updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {

        return ResponseEntity.ok(
                volunteerService.updateApplicationStatus(
                        applicationId,
                        status
                )
        );
    }
}