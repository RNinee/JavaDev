package com.springboot.devconnector.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.devconnector.utils.FlexibleDateDeserializer;
import com.springboot.devconnector.utils.ObjectIdSerializer;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Education {

    @JsonProperty("_id")
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String school;
    private String degree;
    private String fieldOfStudy;

    @JsonDeserialize(using = FlexibleDateDeserializer.class)
    private LocalDate from;

    @JsonDeserialize(using = FlexibleDateDeserializer.class)
    private LocalDate to;

    private Boolean current;
    private String description;

    // No-args constructor
    public Education() {
        this.id = new ObjectId();
    }

    // All-args constructor
    public Education(String school, String degree, String fieldOfStudy, LocalDate from, LocalDate to, Boolean current, String description) {
        this.id = new ObjectId();
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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
