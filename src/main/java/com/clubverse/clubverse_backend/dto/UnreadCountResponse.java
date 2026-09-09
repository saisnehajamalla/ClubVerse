package com.clubverse.clubverse_backend.dto;

public class UnreadCountResponse {

    private long unreadCount;

    public UnreadCountResponse() {
    }

    public UnreadCountResponse(long unreadCount) {
        this.unreadCount = unreadCount;
    }

    public long getUnreadCount() {
        return unreadCount;
    }
}