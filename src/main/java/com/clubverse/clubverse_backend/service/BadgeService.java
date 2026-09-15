package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.BadgeResponse;
import com.clubverse.clubverse_backend.dto.UserBadgeResponse;
import com.clubverse.clubverse_backend.entity.Badge;
import com.clubverse.clubverse_backend.entity.NotificationType;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.entity.UserBadge;
import com.clubverse.clubverse_backend.repository.BadgeRepository;
import com.clubverse.clubverse_backend.repository.UserBadgeRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final UserRepository userRepository;
    private final RewardService rewardService;
    private final NotificationService notificationService;

    public BadgeService(
            BadgeRepository badgeRepository,
            UserBadgeRepository userBadgeRepository,
            UserRepository userRepository,
            RewardService rewardService,
            NotificationService notificationService) {
        this.badgeRepository = badgeRepository;
        this.userBadgeRepository = userBadgeRepository;
        this.userRepository = userRepository;
        this.rewardService = rewardService;
        this.notificationService = notificationService;
    }

    /**
     * Get all available badges
     */
    public List<BadgeResponse> getAllBadges() {
        return badgeRepository.findAll()
                .stream()
                .map(this::toBadgeResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get badges earned by a user
     */
    public List<UserBadgeResponse> getUserBadges(String userEmail) {
        return userBadgeRepository.findByUserEmailOrderByAwardedAtDesc(userEmail)
                .stream()
                .map(this::toUserBadgeResponse)
                .collect(Collectors.toList());
    }

    /**
     * Award a badge to a user (admin operation)
     * Checks eligibility based on reward points
     */
    public UserBadgeResponse awardBadge(String recipientEmail, Long badgeId) {
        User recipient = userRepository.findByEmail(recipientEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + recipientEmail));

        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new IllegalArgumentException("Badge not found: " + badgeId));

        // Check if user already has this badge
        if (userBadgeRepository.findByUserEmailAndBadgeId(recipientEmail, badgeId).isPresent()) {
            throw new IllegalArgumentException("User already has this badge");
        }

        // Check eligibility: user must have enough reward points
        Long userBalance = rewardService.getRewardBalance(recipientEmail);
        if (userBalance < badge.getRequiredRewardPoints()) {
            throw new IllegalArgumentException(
                    "User does not have enough points. Required: " + badge.getRequiredRewardPoints() +
                            ", Current: " + userBalance);
        }

        // Award the badge
        UserBadge userBadge = new UserBadge();
        userBadge.setUser(recipient);
        userBadge.setBadge(badge);
        userBadge.setAwardedAt(LocalDateTime.now());

        UserBadge saved = userBadgeRepository.save(userBadge);

        // Notify user about the badge
        notificationService.createNotification(
                recipient,
                "Badge Earned: " + badge.getName(),
                "Congratulations! You've earned the \"" + badge.getName() + "\" badge: " + badge.getDescription(),
                NotificationType.BADGE
        );

        return toUserBadgeResponse(saved);
    }

    /**
     * Evaluate which badges a user is eligible for based on reward points
     * Returns list of badges user could earn if points are awarded
     */
    public List<BadgeResponse> getEligibleBadges(String userEmail) {
        Long userBalance = rewardService.getRewardBalance(userEmail);
        List<Long> earnedBadgeIds = userBadgeRepository.findByUserEmailOrderByAwardedAtDesc(userEmail)
                .stream()
                .map(ub -> ub.getBadge().getId())
                .collect(Collectors.toList());

        return badgeRepository.findAll()
                .stream()
                .filter(badge -> !earnedBadgeIds.contains(badge.getId()) && userBalance >= badge.getRequiredRewardPoints())
                .map(this::toBadgeResponse)
                .collect(Collectors.toList());
    }

    private BadgeResponse toBadgeResponse(Badge badge) {
        return new BadgeResponse(
                badge.getId(),
                badge.getName(),
                badge.getDescription(),
                badge.getRequiredRewardPoints(),
                badge.getIcon()
        );
    }

    private UserBadgeResponse toUserBadgeResponse(UserBadge userBadge) {
        return new UserBadgeResponse(
                userBadge.getId(),
                toBadgeResponse(userBadge.getBadge()),
                userBadge.getAwardedAt()
        );
    }
}
