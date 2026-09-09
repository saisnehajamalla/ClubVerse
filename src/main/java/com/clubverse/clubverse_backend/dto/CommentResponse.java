package com.clubverse.clubverse_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {

    private Long id;
    private String content;
    private boolean anonymous;
    private int likeCount;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private Long parentCommentId;
    private List<CommentResponse> replies;

    public CommentResponse() {
    }

    public CommentResponse(Long id,
                           String content,
                           boolean anonymous,
                           int likeCount,
                           LocalDateTime createdAt,
                           Long userId,
                           String userName,
                           Long parentCommentId,
                           List<CommentResponse> replies) {

        this.id = id;
        this.content = content;
        this.anonymous = anonymous;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.userId = userId;
        this.userName = userName;
        this.parentCommentId = parentCommentId;
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
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

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public List<CommentResponse> getReplies() {
        return replies;
    }
}