package com.springboot.devconnector.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.devconnector.dto.auth.AuthResponse;
import com.springboot.devconnector.dto.auth.LoginRequest;
import com.springboot.devconnector.dto.auth.RegisterRequest;
import com.springboot.devconnector.dto.user.UserResponse;
import com.springboot.devconnector.models.User;
import com.springboot.devconnector.repositories.UserRepository;
import com.springboot.devconnector.security.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        String avatar = getGravatarUrl(registerRequest.getEmail());

        User user = new User(
                registerRequest.getName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                avatar
        );

        user = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(user.getId());

        return new AuthResponse(token);
    }

    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatar(),
                user.getDate()
        );
    }

    private String getGravatarUrl(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(email.toLowerCase().trim().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return "https://www.gravatar.com/avatar/" + hexString.toString() + "?s=200&d=mm&r=pg";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating avatar URL", e);
        }
    }
}
