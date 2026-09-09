package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.ModerationDecision;
import com.clubverse.clubverse_backend.entity.ModerationResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModerationResultRepository extends JpaRepository<ModerationResult, Long> {

    List<ModerationResult> findByDecision(ModerationDecision decision);
}