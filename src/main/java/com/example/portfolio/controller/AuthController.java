package com.example.portfolio.controller;

import com.example.portfolio.dto.LoginRequest;
import com.example.portfolio.entity.Admin;
import com.example.portfolio.jwt.JwtUtil;
import com.example.portfolio.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AdminRepository adminRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (admin == null || !isPasswordValid(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(admin.getUsername());

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token
        ));
    }

    private boolean isPasswordValid(String rawPassword, String savedPassword) {
        if (rawPassword == null || savedPassword == null) {
            return false;
        }

        if (savedPassword.startsWith("$2a$") || savedPassword.startsWith("$2b$") || savedPassword.startsWith("$2y$")) {
            return passwordEncoder.matches(rawPassword, savedPassword);
        }

        return savedPassword.equals(rawPassword);
    }
}
