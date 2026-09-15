package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    /**
     * Find all rewards for a user by email, ordered by newest first
     */
    @Query("SELECT r FROM Reward r WHERE r.user.email = :email ORDER BY r.createdAt DESC")
    List<Reward> findByUserEmailOrderByCreatedAtDesc(String email);

    /**
     * Calculate total reward balance for a user
     */
    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM Reward r WHERE r.user.email = :email")
    Long getRewardBalance(String email);
}
