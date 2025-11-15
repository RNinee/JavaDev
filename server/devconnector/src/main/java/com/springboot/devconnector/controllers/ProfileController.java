package com.springboot.devconnector.controllers;

import com.springboot.devconnector.dto.github.GitHubRepo;
import com.springboot.devconnector.dto.profile.ProfileRequest;
import com.springboot.devconnector.dto.profile.ProfileResponse;
import com.springboot.devconnector.models.Education;
import com.springboot.devconnector.models.Experience;
import com.springboot.devconnector.models.User;
import com.springboot.devconnector.repositories.UserRepository;
import com.springboot.devconnector.services.GitHubService;
import com.springboot.devconnector.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GitHubService gitHubService;

    // @route   GET api/profile/me
    // @desc    Get current users profile
    // @access  Private
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse profile = profileService.getProfileByUserId(user.getId());
        return ResponseEntity.ok(profile);
    }

    // @route   POST api/profile
    // @desc    Create or update user profile
    // @access  Private
    @PostMapping({"", "/"})
    public ResponseEntity<ProfileResponse> createOrUpdateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProfileRequest profileRequest) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String userId = Objects.requireNonNull(user.getId(), "User ID not found");
        ProfileResponse profile = profileService.createOrUpdateProfile(userId, profileRequest);
        return ResponseEntity.ok(profile);
    }

    // @route   GET api/profile
    // @desc    Get all profiles
    // @access  Public  
    @GetMapping({"", "/"})
    public ResponseEntity<List<ProfileResponse>> getAllProfiles() {
        List<ProfileResponse> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    // @route   GET api/profile/user/{userId}
    // @desc    Get profile by user ID
    // @access  Public
    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getProfileByUserId(@PathVariable String userId) {
        ProfileResponse profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    // @route   DELETE api/profile
    // @desc    Delete profile, user & posts
    // @access  Private
    @DeleteMapping({"", "/"})
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        profileService.deleteProfile(user.getId());
        return ResponseEntity.ok().build();
    }

    // @route   PUT api/profile/experience
    // @desc    Add profile experience
    // @access  Private
    @PutMapping("/experience")
    public ResponseEntity<ProfileResponse> addExperience(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Experience experience) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse profile = profileService.addExperience(user.getId(), experience);
        return ResponseEntity.ok(profile);
    }

    // @route   DELETE api/profile/experience/{expId}
    // @desc    Delete experience from profile
    // @access  Private
    @DeleteMapping("/experience/{expId}")
    public ResponseEntity<ProfileResponse> deleteExperience(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String expId) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse profile = profileService.deleteExperience(user.getId(), expId);
        return ResponseEntity.ok(profile);
    }

    // @route   PUT api/profile/education
    // @desc    Add profile education
    // @access  Private
    @PutMapping("/education")
    public ResponseEntity<ProfileResponse> addEducation(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Education education) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse profile = profileService.addEducation(user.getId(), education);
        return ResponseEntity.ok(profile);
    }

    // @route   DELETE api/profile/education/{eduId}
    // @desc    Delete education from profile
    // @access  Private
    @DeleteMapping("/education/{eduId}")
    public ResponseEntity<ProfileResponse> deleteEducation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String eduId) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse profile = profileService.deleteEducation(user.getId(), eduId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/github/{username}")
    public ResponseEntity<List<GitHubRepo>> getGitHubRepos(@PathVariable String username) {
        List<GitHubRepo> repos = gitHubService.getUserRepos(username);
        return ResponseEntity.ok(repos);
    }
}
