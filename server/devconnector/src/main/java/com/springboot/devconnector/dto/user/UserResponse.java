package com.springboot.devconnector.dto.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonProperty("_id")
    private String id;
    private String name;
    private String email;
    private String avatar;
    private LocalDateTime date;

    // No-args constructor
    public UserResponse() {
    }

    // All-args constructor
    public UserResponse(String id, String name, String email, String avatar, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.date = date;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
