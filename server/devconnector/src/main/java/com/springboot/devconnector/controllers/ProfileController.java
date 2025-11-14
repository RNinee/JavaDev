package com.springboot.devconnector.controllers;

import com.springboot.devconnector.dto.profile.ProfileRequest;
import com.springboot.devconnector.dto.profile.ProfileResponse;
import com.springboot.devconnector.models.User;
import com.springboot.devconnector.repositories.UserRepository;
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

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse profile = profileService.getProfileByUserId(user.getId());
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/")
    public ResponseEntity<ProfileResponse> createOrUpdateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProfileRequest profileRequest) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String userId = Objects.requireNonNull(user.getId(), "User ID not found");
        ProfileResponse profile = profileService.createOrUpdateProfile(userId, profileRequest);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProfileResponse>> getAllProfiles() {
        List<ProfileResponse> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getProfileByUserId(@PathVariable String userId) {
        ProfileResponse profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        profileService.deleteProfile(user.getId());
        return ResponseEntity.ok().build();
    }
}
