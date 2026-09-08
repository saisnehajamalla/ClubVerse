package com.clubverse.clubverse_backend.dto;

public class PostRequest {

    private String content;
    private boolean anonymous;

    public PostRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}