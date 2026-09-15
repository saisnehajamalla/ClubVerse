package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.RewardResponse;
import com.clubverse.clubverse_backend.service.RewardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Award points to a user (admin only)
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'CLUB_ADMIN')")
    public ResponseEntity<RewardResponse> awardReward(
            @RequestParam String recipientEmail,
            @RequestParam Long amount,
            @RequestParam String reason) {
        try {
            RewardResponse response = rewardService.awardReward(recipientEmail, amount, reason);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get authenticated user's rewards
     */
    @GetMapping
    public ResponseEntity<List<RewardResponse>> getUserRewards(Authentication authentication) {
        String userEmail = authentication.getName();
        List<RewardResponse> rewards = rewardService.getUserRewards(userEmail);
        return ResponseEntity.ok(rewards);
    }

    /**
     * Get authenticated user's reward balance
     */
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Long>> getRewardBalance(Authentication authentication) {
        String userEmail = authentication.getName();
        Long balance = rewardService.getRewardBalance(userEmail);
        Map<String, Long> response = new HashMap<>();
        response.put("balance", balance);
        return ResponseEntity.ok(response);
    }
}
