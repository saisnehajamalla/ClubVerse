package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.RewardResponse;
import com.clubverse.clubverse_backend.entity.Notification;
import com.clubverse.clubverse_backend.entity.NotificationType;
import com.clubverse.clubverse_backend.entity.Reward;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.RewardRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public RewardService(RewardRepository rewardRepository, UserRepository userRepository, NotificationService notificationService) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    /**
     * Award points to a user (admin operation)
     * @param recipientEmail Email of the user receiving the reward
     * @param amount Points to award
     * @param reason Description of why the reward is being given
     * @return RewardResponse
     */
    public RewardResponse awardReward(String recipientEmail, Long amount, String reason) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Reward amount must be positive");
        }

        User recipient = userRepository.findByEmail(recipientEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + recipientEmail));

        Reward reward = new Reward();
        reward.setUser(recipient);
        reward.setAmount(amount);
        reward.setReason(reason);
        reward.setCreatedAt(LocalDateTime.now());

        Reward saved = rewardRepository.save(reward);

        // Notify user about the reward
        notificationService.createNotification(
                recipient,
                "Reward Earned",
                "You have earned " + amount + " points for: " + reason,
                NotificationType.REWARD
        );

        return toRewardResponse(saved);
    }

    /**
     * Get all rewards for the authenticated user
     * @param userEmail Email of the user
     * @return List of rewards ordered by newest first
     */
    public List<RewardResponse> getUserRewards(String userEmail) {
        return rewardRepository.findByUserEmailOrderByCreatedAtDesc(userEmail)
                .stream()
                .map(this::toRewardResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get the total reward balance for a user
     * @param userEmail Email of the user
     * @return Total points balance
     */
    public Long getRewardBalance(String userEmail) {
        return rewardRepository.getRewardBalance(userEmail);
    }

    private RewardResponse toRewardResponse(Reward reward) {
        return new RewardResponse(
                reward.getId(),
                reward.getAmount(),
                reward.getReason(),
                reward.getCreatedAt()
        );
    }
}
