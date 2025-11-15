package com.springboot.devconnector.dto.like;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeResponse {

    @JsonProperty("_id")
    private String id;

    private String user;

    

    public LikeResponse(String id, String user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LikeResponse() {
    }

    
}
