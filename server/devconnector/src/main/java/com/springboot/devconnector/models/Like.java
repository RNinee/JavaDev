package com.springboot.devconnector.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.devconnector.utils.ObjectIdSerializer;

public class Like {
    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @DBRef
    private User user;

    public Like(ObjectId id, User user) {
        this.id = id;
        this.user = user;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Like(User user) {
        this.id = new ObjectId();
        this.user = user;
    }   

    public Like() {
    }

    
    
}
