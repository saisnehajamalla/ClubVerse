package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String community;
    private String title;
    private String content;
    private String mediaUrl;
    private boolean anonymous;
    private int likeCount;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;

    public PostResponse() {
    }

    public PostResponse(Long id,
                        String community,
                        String title,
                        String content,
                        String mediaUrl,
                        boolean anonymous,
                        int likeCount,
                        LocalDateTime createdAt,
                        Long userId,
                        String userName) {

        this.id = id;
        this.community = community;
        this.title = title;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.anonymous = anonymous;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.userId = userId;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getCommunity() {
        return community;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}