package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.RegistrationResponse;
import com.clubverse.clubverse_backend.entity.Event;
import com.clubverse.clubverse_backend.entity.Registration;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.EventRepository;
import com.clubverse.clubverse_backend.repository.RegistrationRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            EventRepository eventRepository,
            UserRepository userRepository) {

        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public RegistrationResponse register(
            Long eventId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        if (registrationRepository
                .findByEventIdAndUserId(eventId, user.getId())
                .isPresent()) {

            throw new RuntimeException(
                    "User already registered for this event");
        }

        if (event.getRegistrationDeadline() != null
                && event.getRegistrationDeadline()
                .isBefore(java.time.LocalDate.now())) {

            throw new RuntimeException(
                    "Registration deadline has passed");
        }

        if (event.getMaxRegistrations() != null
                && event.getRegisteredCount()
                >= event.getMaxRegistrations()) {

            throw new RuntimeException(
                    "Event registration is full");
        }

        Registration registration = new Registration();

        registration.setEvent(event);
        registration.setUser(user);
        registration.setStatus(
                Registration.Status.CONFIRMED
        );
        registration.setRegisteredAt(
                LocalDateTime.now()
        );

        Registration savedRegistration =
                registrationRepository.save(registration);

        event.setRegisteredCount(
                event.getRegisteredCount() + 1
        );

        eventRepository.save(event);

        return convertToResponse(savedRegistration);
    }

    public List<RegistrationResponse> getEventRegistrations(
            Long eventId) {

        return registrationRepository
                .findByEventId(eventId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<RegistrationResponse> getUserRegistrations(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return registrationRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private RegistrationResponse convertToResponse(
            Registration registration) {

        User user = registration.getUser();

        return new RegistrationResponse(
                registration.getId(),
                registration.getEvent().getId(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                registration.getStatus().name(),
                registration.getRegisteredAt()
        );
    }
}