package com.springboot.devconnector.dto.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentResponse {
    @JsonProperty("_id")
    private String id;
    private String text;
    private String name;
    private String avatar;
    private String user; 
    private LocalDateTime date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CommentResponse(String id, String text, String name, String avatar, String user, LocalDateTime date) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.avatar = avatar;
        this.user = user;
        this.date = date;
    }
}