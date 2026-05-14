package com.nextround.auth_service.service;

import com.nextround.auth_service.config.JwtUtil;
import com.nextround.auth_service.dto.LoginRequest;
import com.nextround.auth_service.dto.RegisterRequest;
import com.nextround.auth_service.entity.User;
import com.nextround.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        if (repo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (repo.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .isVerified(false)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        repo.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {

        User user = repo.findByEmailOrPhoneNumber(
                        request.getEmailOrPhone(),
                        request.getEmailOrPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user);
    }
}