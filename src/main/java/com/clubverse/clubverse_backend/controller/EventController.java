package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.EventRequest;
import com.clubverse.clubverse_backend.dto.EventResponse;
import com.clubverse.clubverse_backend.service.EventService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody EventRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        EventResponse response = eventService.createEvent(request, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents()
        );
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                eventService.getEvent(eventId)
        );
    }
}