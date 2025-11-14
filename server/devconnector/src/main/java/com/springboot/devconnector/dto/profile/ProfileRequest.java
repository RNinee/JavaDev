package com.springboot.devconnector.dto.profile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.springboot.devconnector.models.Education;
import com.springboot.devconnector.models.Experience;
import com.springboot.devconnector.models.Social;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProfileRequest {

    private String company;
    private String website;
    private String location;

    @NotNull(message = "Status is required")
    private String status;

    @NotEmpty(message = "Skills is required")
    private List<String> skills;

    private String bio;
    private String githubUsername;

    private List<Experience> experience;
    private List<Education> education;
    private Social social;

    // No-args constructor
    public ProfileRequest() {
    }

    // All-args constructor
    public ProfileRequest(String company, String website, String location, String status, List<String> skills,
            String bio, String githubUsername, List<Experience> experience, List<Education> education, Social social) {
        this.company = company;
        this.website = website;
        this.location = location;
        this.status = status;
        this.skills = skills;
        this.bio = bio;
        this.githubUsername = githubUsername;
        this.experience = experience;
        this.education = education;
        this.social = social;
    }

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    // Custom setter to handle comma-separated string
    @JsonSetter("skills")
    public void setSkillsFromString(Object skills) {
        if (skills instanceof String) {
            // If it's a string, split by comma and trim whitespace
            this.skills = Arrays.stream(((String) skills).split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } else if (skills instanceof List<?>) {
            // If it's already a list, convert it to List<String>
            List<?> rawList = (List<?>) skills;
            this.skills = rawList.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public Social getSocial() {
        return social;
    }

    public void setSocial(Social social) {
        this.social = social;
    }
}
