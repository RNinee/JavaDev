package com.springboot.devconnector.dto.profile;

import com.springboot.devconnector.dto.user.UserResponse;
import com.springboot.devconnector.models.Education;
import com.springboot.devconnector.models.Experience;
import com.springboot.devconnector.models.Social;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
