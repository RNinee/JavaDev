package com.springboot.devconnector.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.devconnector.utils.FlexibleDateDeserializer;
import com.springboot.devconnector.utils.ObjectIdSerializer;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Experience {

    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String title;
    private String company;
    private String location;

    @JsonDeserialize(using = FlexibleDateDeserializer.class)
    private LocalDate from;

    @JsonDeserialize(using = FlexibleDateDeserializer.class)
    private LocalDate to;

    private Boolean current;
    private String description;

    // No-args constructor
    public Experience() {
        this.id = new ObjectId();
    }

    // All-args constructor
    public Experience(String title, String company, String location, LocalDate from, LocalDate to, Boolean current, String description) {
        this.id = new ObjectId();
        this.title = title;
        this.company = company;
        this.location = location;
        this.from = from;
        this.to = to;
        this.current = current;
        this.description = description;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
