package com.clubverse.clubverse_backend.service;

public class ModerationRejectedException extends RuntimeException {

    public ModerationRejectedException() {
        super("Post rejected by content moderation");
    }
}