package com.clubverse.clubverse_backend.service;

public class ModerationReviewRequiredException extends RuntimeException {

    public ModerationReviewRequiredException() {
        super("Post is awaiting moderation review");
    }
}