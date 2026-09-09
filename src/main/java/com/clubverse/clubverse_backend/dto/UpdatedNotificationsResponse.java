package com.clubverse.clubverse_backend.dto;

public class UpdatedNotificationsResponse {

    private long updatedCount;

    public UpdatedNotificationsResponse() {
    }

    public UpdatedNotificationsResponse(long updatedCount) {
        this.updatedCount = updatedCount;
    }

    public long getUpdatedCount() {
        return updatedCount;
    }
}