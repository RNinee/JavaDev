package com.springboot.devconnector.dto.post;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.devconnector.dto.comment.CommentResponse;
import com.springboot.devconnector.dto.like.LikeResponse;

public class PostResponse {
    @JsonProperty("_id")
    private String id;

    private String text;

    private String name;
    private String avatar;
    private String user;

    List<LikeResponse> likes;
    List<CommentResponse> comments;
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

    public List<LikeResponse> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeResponse> likes) {
        this.likes = likes;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PostResponse() {
    }

    public PostResponse(String id, String text, String name, String avatar, String user, List<LikeResponse> likes,
            List<CommentResponse> comments, LocalDateTime date) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.avatar = avatar;
        this.user = user;
        this.likes = likes;
        this.comments = comments;
        this.date = date;
    }

    

}
