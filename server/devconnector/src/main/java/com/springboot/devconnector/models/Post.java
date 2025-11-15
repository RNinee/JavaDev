package com.springboot.devconnector.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @DBRef
    private User user;

    public Post() {
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Post(String id, User user, String text, String name, String avatar, List<Like> likes, List<Comment> comments,
            LocalDateTime date) {
        this();
        this.id = id;
        this.user = user;
        this.text = text;
        this.name = name;
        this.avatar = avatar;
        this.likes = likes != null ? likes : new ArrayList<>();
        this.comments = comments != null ? comments : new ArrayList<>();
        this.date = date;
    }

    private String text;

    private String name;
    private String avatar;

    private List<Like> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Like> getLikes() {
        if (likes == null) {
            likes = new ArrayList<>();
        }
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes != null ? likes : new ArrayList<>();
    }

    public List<Comment> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    
}
