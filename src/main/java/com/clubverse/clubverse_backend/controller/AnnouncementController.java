package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.AnnouncementRequest;
import com.clubverse.clubverse_backend.dto.AnnouncementResponse;
import com.clubverse.clubverse_backend.service.AnnouncementService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(
            AnnouncementService announcementService) {

        this.announcementService = announcementService;
    }

    // Create and publish/schedule an announcement
    @PostMapping
    public ResponseEntity<AnnouncementResponse> createAnnouncement(
            @RequestBody AnnouncementRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                announcementService.createAnnouncement(
                        request,
                        email
                )
        );
    }

    // Save an announcement as draft
    @PostMapping("/draft")
    public ResponseEntity<AnnouncementResponse> saveDraft(
            @RequestBody AnnouncementRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                announcementService.saveDraft(
                        request,
                        email
                )
        );
    }

    // Get all announcements
    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>>
    getAllAnnouncements() {

        return ResponseEntity.ok(
                announcementService.getAllAnnouncements()
        );
    }

    // Get published announcements for students
    @GetMapping("/published")
    public ResponseEntity<List<AnnouncementResponse>>
    getPublishedAnnouncements() {

        return ResponseEntity.ok(
                announcementService.getPublishedAnnouncements()
        );
    }

    // Get one announcement
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse>
    getAnnouncement(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                announcementService.getAnnouncement(id)
        );
    }

    // Update announcement status
    @PutMapping("/{id}/status")
    public ResponseEntity<AnnouncementResponse>
    updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                announcementService.updateStatus(
                        id,
                        status
                )
        );
    }
}