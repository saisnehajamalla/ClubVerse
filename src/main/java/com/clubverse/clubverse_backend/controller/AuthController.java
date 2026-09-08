package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.RegisterRequest;
import com.clubverse.clubverse_backend.dto.RegisterResponse;
import com.clubverse.clubverse_backend.dto.LoginRequest;
import com.clubverse.clubverse_backend.dto.LoginResponse;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        RegisterResponse response = new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRollNumber(),
                user.getBatch(),
                user.getDepartment(),
                user.getRole().name()
        );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody LoginRequest request) {

    LoginResponse response = authService.login(request);

    return ResponseEntity.ok(response);
    }
}