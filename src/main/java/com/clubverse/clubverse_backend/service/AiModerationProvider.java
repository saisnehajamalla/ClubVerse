package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.entity.ModerationResult;

public interface AiModerationProvider {

    ModerationResult moderate(String content);
}