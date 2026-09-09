package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.entity.ModerationDecision;
import com.clubverse.clubverse_backend.entity.ModerationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OpenAiModerationProvider implements AiModerationProvider {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String apiUrl;
    private final String apiKey;
    private final String model;

    public OpenAiModerationProvider(
            RestClient.Builder restClientBuilder,
            ObjectMapper objectMapper,
            @Value("${ai.moderation.url:https://api.openai.com/v1/moderations}") String apiUrl,
            @Value("${ai.moderation.api-key:}") String apiKey,
            @Value("${ai.moderation.model:omni-moderation-latest}") String model) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.model = model;
    }

    @Override
    public ModerationResult moderate(String content) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("AI moderation API key is not configured");
        }

        String response = restClient.post()
                .uri(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(Map.of("model", model, "input", content))
                .retrieve()
                .body(String.class);

        return toModerationResult(response);
    }

    private ModerationResult toModerationResult(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode firstResult = root.path("results").path(0);

            if (firstResult.isMissingNode()) {
                throw new IllegalStateException("AI moderation response contained no result");
            }

            boolean flagged = firstResult.path("flagged").asBoolean(false);
            JsonNode categories = firstResult.path("categories");

            ModerationResult result = new ModerationResult();
            result.setDecision(flagged ? ModerationDecision.REJECT : ModerationDecision.APPROVE);
            result.setProvider("openai");
            result.setReason(flagged ? categories.toString() : "No moderation category was flagged");
            result.setCreatedAt(LocalDateTime.now());
            return result;
        } catch (Exception exception) {
            throw new IllegalStateException("Invalid AI moderation response", exception);
        }
    }
}