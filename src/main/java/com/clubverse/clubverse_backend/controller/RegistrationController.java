package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.RegistrationResponse;
import com.clubverse.clubverse_backend.service.RegistrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(
            RegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @PostMapping("/{eventId}/registrations")
    public ResponseEntity<RegistrationResponse> register(
            @PathVariable Long eventId,
            Authentication authentication) {

        String email = authentication.getName();

        RegistrationResponse response =
                registrationService.register(eventId, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<List<RegistrationResponse>>
    getEventRegistrations(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                registrationService.getEventRegistrations(eventId)
        );
    }

    @GetMapping("/my-registrations")
    public ResponseEntity<List<RegistrationResponse>>
    getMyRegistrations(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                registrationService.getUserRegistrations(email)
        );
    }
}