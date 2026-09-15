package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

    /**
     * Find all badges for a user by email, ordered by newest first
     */
    @Query("SELECT ub FROM UserBadge ub WHERE ub.user.email = :email ORDER BY ub.awardedAt DESC")
    List<UserBadge> findByUserEmailOrderByAwardedAtDesc(String email);

    /**
     * Check if user already has a badge
     */
    @Query("SELECT ub FROM UserBadge ub WHERE ub.user.email = :email AND ub.badge.id = :badgeId")
    Optional<UserBadge> findByUserEmailAndBadgeId(String email, Long badgeId);
}
