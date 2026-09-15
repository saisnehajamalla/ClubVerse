package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.BadgeResponse;
import com.clubverse.clubverse_backend.dto.UserBadgeResponse;
import com.clubverse.clubverse_backend.service.BadgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    /**
     * Get all available badges
     */
    @GetMapping
    public ResponseEntity<List<BadgeResponse>> getAllBadges() {
        List<BadgeResponse> badges = badgeService.getAllBadges();
        return ResponseEntity.ok(badges);
    }

    /**
     * Get badges earned by authenticated user
     */
    @GetMapping("/user")
    public ResponseEntity<List<UserBadgeResponse>> getUserBadges(Authentication authentication) {
        String userEmail = authentication.getName();
        List<UserBadgeResponse> badges = badgeService.getUserBadges(userEmail);
        return ResponseEntity.ok(badges);
    }

    /**
     * Get badges the authenticated user is eligible for
     */
    @GetMapping("/eligible")
    public ResponseEntity<List<BadgeResponse>> getEligibleBadges(Authentication authentication) {
        String userEmail = authentication.getName();
        List<BadgeResponse> badges = badgeService.getEligibleBadges(userEmail);
        return ResponseEntity.ok(badges);
    }

    /**
     * Award a badge to a user (admin only)
     */
    @PostMapping("/{badgeId}/award")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'CLUB_ADMIN')")
    public ResponseEntity<UserBadgeResponse> awardBadge(
            @PathVariable Long badgeId,
            @RequestParam String recipientEmail) {
        try {
            UserBadgeResponse response = badgeService.awardBadge(recipientEmail, badgeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
