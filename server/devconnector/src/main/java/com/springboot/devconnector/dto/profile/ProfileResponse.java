package com.springboot.devconnector.dto.profile;

import com.springboot.devconnector.dto.user.UserResponse;
import com.springboot.devconnector.models.Education;
import com.springboot.devconnector.models.Experience;
import com.springboot.devconnector.models.Social;

import java.time.LocalDateTime;
import java.util.List;

public class ProfileResponse {

    private String id;
    private UserResponse user;
    private String company;
    private String website;
    private String location;
    private String status;
    private List<String> skills;
    private String bio;
    private String githubUsername;
    private List<Experience> experience;
    private List<Education> education;
    private Social social;
    private LocalDateTime date;

    // No-args constructor
    public ProfileResponse() {
    }

    // All-args constructor
    public ProfileResponse(String id, UserResponse user, String company, String website, String location, String status, List<String> skills, String bio, String githubUsername, List<Experience> experience, List<Education> education, Social social, LocalDateTime date) {
        this.id = id;
        this.user = user;
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
        this.date = date;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
