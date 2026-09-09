package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.entity.ModerationDecision;
import com.clubverse.clubverse_backend.entity.ModerationResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModerationService {

    private final AiModerationProvider aiModerationProvider;

    public ModerationService(AiModerationProvider aiModerationProvider) {
        this.aiModerationProvider = aiModerationProvider;
    }

    public ModerationResult moderate(String content) {
        try {
            ModerationResult result = aiModerationProvider.moderate(content);
            if (result == null || result.getDecision() == null) {
                return reviewResult("AI provider returned no moderation decision");
            }
            return result;
        } catch (Exception exception) {
            return reviewResult("AI moderation provider failed: " + exception.getMessage());
        }
    }

    private ModerationResult reviewResult(String reason) {
        ModerationResult result = new ModerationResult();
        result.setDecision(ModerationDecision.REVIEW);
        result.setProvider("fallback");
        result.setReason(reason);
        result.setCreatedAt(LocalDateTime.now());
        return result;
    }
}