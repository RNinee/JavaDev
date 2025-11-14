package com.springboot.devconnector.dto.profile;

import com.springboot.devconnector.models.Education;
import com.springboot.devconnector.models.Experience;
import com.springboot.devconnector.models.Social;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
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
}
