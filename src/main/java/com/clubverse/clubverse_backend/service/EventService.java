package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.EventRequest;
import com.clubverse.clubverse_backend.dto.EventResponse;
import com.clubverse.clubverse_backend.entity.Event;
import com.clubverse.clubverse_backend.repository.EventRepository;
import org.springframework.stereotype.Service;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(
        EventRepository eventRepository,
        UserRepository userRepository) {

    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    }

    public EventResponse createEvent(
            EventRequest request,
            String email) {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        Long organizerId = user.getId();

        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setPosterUrl(request.getPosterUrl());
        event.setEventDate(request.getEventDate());
        event.setVenue(request.getVenue());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setEligibility(request.getEligibility());
        event.setMaxRegistrations(request.getMaxRegistrations());
        event.setRegisteredCount(0);
        event.setRegistrationDeadline(
                request.getRegistrationDeadline()
        );
        event.setVolunteerRegistrationEnabled(
                request.isVolunteerRegistrationEnabled()
        );
        event.setRewardsEnabled(
                request.isRewardsEnabled()
        );
        event.setApprovalRequired(
                request.isApprovalRequired()
        );
        event.setRewardXp(
                request.getRewardXp()
        );
        event.setFeatured(
                request.isFeatured()
        );
        event.setFreeEntry(
                request.isFreeEntry()
        );
        event.setTags(request.getTags());

        event.setOrganizerId(organizerId);

        event.setStatus(
                request.isApprovalRequired()
                        ? Event.Status.PENDING
                        : Event.Status.PUBLISHED
        );

        event.setCreatedAt(LocalDateTime.now());

        Event savedEvent = eventRepository.save(event);

        return convertToResponse(savedEvent);
    }

    public List<EventResponse> getAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public EventResponse getEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        return convertToResponse(event);
    }

    private EventResponse convertToResponse(Event event) {

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getPosterUrl(),
                event.getEventDate(),
                event.getVenue(),
                event.getStartTime(),
                event.getEndTime(),
                event.getEligibility(),
                event.getMaxRegistrations(),
                event.getRegisteredCount(),
                event.getRegistrationDeadline(),
                event.isVolunteerRegistrationEnabled(),
                event.isRewardsEnabled(),
                event.isApprovalRequired(),
                event.getRewardXp(),
                event.isFeatured(),
                event.isFreeEntry(),
                event.getTags(),
                event.getOrganizerId(),
                event.getStatus().name(),
                event.getCreatedAt()
        );
    }
}