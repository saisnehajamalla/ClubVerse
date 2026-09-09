package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.entity.ModerationDecision;
import com.clubverse.clubverse_backend.entity.ModerationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModerationServiceTest {

    @Mock
    private AiModerationProvider aiModerationProvider;

    @Test
    void returnsApproveDecision() {
        when(aiModerationProvider.moderate("clean content"))
                .thenReturn(result(ModerationDecision.APPROVE));

        ModerationResult result = new ModerationService(aiModerationProvider)
                .moderate("clean content");

        assertEquals(ModerationDecision.APPROVE, result.getDecision());
    }

    @Test
    void returnsRejectDecision() {
        when(aiModerationProvider.moderate("harmful content"))
                .thenReturn(result(ModerationDecision.REJECT));

        ModerationResult result = new ModerationService(aiModerationProvider)
                .moderate("harmful content");

        assertEquals(ModerationDecision.REJECT, result.getDecision());
    }

    @Test
    void returnsReviewDecision() {
        when(aiModerationProvider.moderate("ambiguous content"))
                .thenReturn(result(ModerationDecision.REVIEW));

        ModerationResult result = new ModerationService(aiModerationProvider)
                .moderate("ambiguous content");

        assertEquals(ModerationDecision.REVIEW, result.getDecision());
    }

    @Test
    void providerFailureFallsBackToReview() {
        when(aiModerationProvider.moderate("unavailable content"))
                .thenThrow(new IllegalStateException("provider unavailable"));

        ModerationResult result = new ModerationService(aiModerationProvider)
                .moderate("unavailable content");

        assertEquals(ModerationDecision.REVIEW, result.getDecision());
        assertEquals("fallback", result.getProvider());
    }

    private ModerationResult result(ModerationDecision decision) {
        ModerationResult result = new ModerationResult();
        result.setDecision(decision);
        result.setProvider("test-provider");
        return result;
    }
}