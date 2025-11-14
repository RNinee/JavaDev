package com.springboot.devconnector.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.devconnector.dto.profile.ProfileRequest;
import com.springboot.devconnector.dto.profile.ProfileResponse;
import com.springboot.devconnector.dto.user.UserResponse;
import com.springboot.devconnector.models.Education;
import com.springboot.devconnector.models.Experience;
import com.springboot.devconnector.models.Profile;
import com.springboot.devconnector.models.User;
import com.springboot.devconnector.repositories.ProfileRepository;
import com.springboot.devconnector.repositories.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public ProfileResponse createOrUpdateProfile(String userId, ProfileRequest profileRequest) {

        if(userId == null) {
            throw new RuntimeException("User ID cannot be null");

        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(userId)
                .orElse(new Profile());

        profile.setUser(user);
        profile.setCompany(profileRequest.getCompany());
        profile.setWebsite(profileRequest.getWebsite());
        profile.setLocation(profileRequest.getLocation());
        profile.setStatus(profileRequest.getStatus());
        profile.setSkills(profileRequest.getSkills());
        profile.setBio(profileRequest.getBio());
        profile.setGithubUsername(profileRequest.getGithubUsername());
        profile.setExperience(profileRequest.getExperience());
        profile.setEducation(profileRequest.getEducation());
        profile.setSocial(profileRequest.getSocial());

        if (profile.getDate() == null) {
            profile.setDate(LocalDateTime.now());
        }

        profile = profileRepository.save(profile);

        return convertToResponse(profile);
    }

    public List<ProfileResponse> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProfileResponse getProfileByUserId(String userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return convertToResponse(profile);
    }

    public void deleteProfile(String userId) {
        if(userId == null) {
            throw new RuntimeException("User ID cannot be null");

        }
        profileRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    public ProfileResponse addExperience(String userId, Experience experience) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<Experience> experiences = profile.getExperience();
        if (experiences == null) {
            experiences = new ArrayList<>();
        }
        experiences.add(0, experience); // Add to beginning of array
        profile.setExperience(experiences);

        profile = profileRepository.save(profile);
        return convertToResponse(profile);
    }

    public ProfileResponse deleteExperience(String userId, String expId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<Experience> experiences = profile.getExperience();
        if (experiences != null) {
            // Remove experience by ObjectId
            experiences.removeIf(exp -> exp.getId().toString().equals(expId));
            profile.setExperience(experiences);
            profile = profileRepository.save(profile);
        }

        return convertToResponse(profile);
    }

    public ProfileResponse addEducation(String userId, Education education) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<Education> educations = profile.getEducation();
        if (educations == null) {
            educations = new ArrayList<>();
        }
        educations.add(0, education); // Add to beginning of array
        profile.setEducation(educations);

        profile = profileRepository.save(profile);
        return convertToResponse(profile);
    }

    public ProfileResponse deleteEducation(String userId, String eduId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<Education> educations = profile.getEducation();
        if (educations != null) {
            // Remove education by ObjectId
            educations.removeIf(edu -> edu.getId().toString().equals(eduId));
            profile.setEducation(educations);
            profile = profileRepository.save(profile);
        }

        return convertToResponse(profile);
    }

    private ProfileResponse convertToResponse(Profile profile) {
        User user = profile.getUser();
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatar(),
                user.getDate()
        );

        return new ProfileResponse(
                profile.getId(),
                userResponse,
                profile.getCompany(),
                profile.getWebsite(),
                profile.getLocation(),
                profile.getStatus(),
                profile.getSkills(),
                profile.getBio(),
                profile.getGithubUsername(),
                profile.getExperience(),
                profile.getEducation(),
                profile.getSocial(),
                profile.getDate()
        );
    }
}
