package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.entity.ModerationDecision;
import com.clubverse.clubverse_backend.entity.ModerationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModerationService {

    @Autowired(required = false)
    private final AiModerationProvider aiModerationProvider;
    private final boolean aiModerationEnabled;

    public ModerationService(@Autowired(required = false) AiModerationProvider aiModerationProvider,
                             @Value("${ai.moderation.enabled:false}") boolean aiModerationEnabled) {
        this.aiModerationProvider = aiModerationProvider;
        this.aiModerationEnabled = aiModerationEnabled;
    }

    public ModerationResult moderate(String content) {
        try {
            if (!aiModerationEnabled) {
                return approveResult("AI moderation disabled");
            }
            if (aiModerationProvider == null) {
                return reviewResult("AI moderation not configured");
            }
            ModerationResult result = aiModerationProvider.moderate(content);
            if (result == null || result.getDecision() == null) {
                return reviewResult("AI provider returned no moderation decision");
            }
            return result;
        } catch (Exception exception) {
            return reviewResult("AI moderation provider failed: " + exception.getMessage());
        }
    }

    private ModerationResult approveResult(String reason) {
        ModerationResult result = new ModerationResult();
        result.setDecision(ModerationDecision.APPROVE);
        result.setProvider("disabled");
        result.setReason(reason);
        result.setCreatedAt(LocalDateTime.now());
        return result;
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