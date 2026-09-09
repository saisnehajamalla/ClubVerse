package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.VolunteerApplicationRequest;
import com.clubverse.clubverse_backend.dto.VolunteerApplicationResponse;
import com.clubverse.clubverse_backend.dto.VolunteerOpportunityRequest;
import com.clubverse.clubverse_backend.dto.VolunteerOpportunityResponse;
import com.clubverse.clubverse_backend.entity.Event;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.entity.VolunteerApplication;
import com.clubverse.clubverse_backend.entity.VolunteerOpportunity;
import com.clubverse.clubverse_backend.repository.EventRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import com.clubverse.clubverse_backend.repository.VolunteerApplicationRepository;
import com.clubverse.clubverse_backend.repository.VolunteerOpportunityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerOpportunityRepository opportunityRepository;
    private final VolunteerApplicationRepository applicationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public VolunteerService(
            VolunteerOpportunityRepository opportunityRepository,
            VolunteerApplicationRepository applicationRepository,
            EventRepository eventRepository,
            UserRepository userRepository) {

        this.opportunityRepository = opportunityRepository;
        this.applicationRepository = applicationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public VolunteerOpportunityResponse createOpportunity(
            VolunteerOpportunityRequest request) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        VolunteerOpportunity opportunity =
                new VolunteerOpportunity();

        opportunity.setEvent(event);
        opportunity.setRole(request.getRole());
        opportunity.setTeam(request.getTeam());
        opportunity.setRequirements(request.getRequirements());
        opportunity.setRewardXp(
                request.getRewardXp() != null
                        ? request.getRewardXp()
                        : 0
        );
        opportunity.setApplicationDeadline(
                request.getApplicationDeadline()
        );
        opportunity.setTotalSlots(
                request.getTotalSlots()
        );
        opportunity.setFilledSlots(0);
        opportunity.setActive(true);

        VolunteerOpportunity saved =
                opportunityRepository.save(opportunity);

        return convertOpportunityToResponse(saved);
    }

    public List<VolunteerOpportunityResponse>
    getAllOpportunities() {

        return opportunityRepository.findByActiveTrue()
                .stream()
                .map(this::convertOpportunityToResponse)
                .toList();
    }

    public List<VolunteerOpportunityResponse>
    getEventOpportunities(Long eventId) {

        return opportunityRepository.findByEventId(eventId)
                .stream()
                .map(this::convertOpportunityToResponse)
                .toList();
    }

    public VolunteerApplicationResponse apply(
            Long opportunityId,
            VolunteerApplicationRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        VolunteerOpportunity opportunity =
                opportunityRepository.findById(opportunityId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Volunteer opportunity not found"));

        if (!opportunity.isActive()) {
            throw new RuntimeException(
                    "Volunteer opportunity is not active");
        }

        if (opportunity.getApplicationDeadline() != null
                && opportunity.getApplicationDeadline()
                    .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Volunteer application deadline has passed");
        }

        if (opportunity.getTotalSlots() != null
                && opportunity.getFilledSlots()
                    >= opportunity.getTotalSlots()) {

            throw new RuntimeException(
                    "Volunteer opportunity is full");
        }

        if (applicationRepository
                .findByOpportunityIdAndUserId(
                        opportunityId,
                        user.getId())
                .isPresent()) {

            throw new RuntimeException(
                    "User already applied for this opportunity");
        }

        VolunteerApplication application =
                new VolunteerApplication();

        application.setOpportunity(opportunity);
        application.setUser(user);
        application.setApplicationMessage(
                request.getApplicationMessage()
        );
        application.setStatus(
                VolunteerApplication.Status.PENDING
        );
        application.setAppliedAt(
                LocalDateTime.now()
        );

        VolunteerApplication saved =
                applicationRepository.save(application);

        return convertApplicationToResponse(saved);
    }

    public List<VolunteerApplicationResponse>
    getOpportunityApplications(Long opportunityId) {

        return applicationRepository
                .findByOpportunityId(opportunityId)
                .stream()
                .map(this::convertApplicationToResponse)
                .toList();
    }

    public List<VolunteerApplicationResponse>
    getMyApplications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return applicationRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::convertApplicationToResponse)
                .toList();
    }

    public VolunteerApplicationResponse updateApplicationStatus(
            Long applicationId,
            String status) {

        VolunteerApplication application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Volunteer application not found"));

        VolunteerApplication.Status newStatus;

        try {
            newStatus =
                    VolunteerApplication.Status.valueOf(
                            status.toUpperCase()
                    );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Invalid status. Use PENDING, ACCEPTED or REJECTED");
        }

        VolunteerApplication.Status oldStatus =
                application.getStatus();

        application.setStatus(newStatus);

        if (oldStatus != VolunteerApplication.Status.ACCEPTED
                && newStatus == VolunteerApplication.Status.ACCEPTED) {

            VolunteerOpportunity opportunity =
                    application.getOpportunity();

            opportunity.setFilledSlots(
                    opportunity.getFilledSlots() + 1
            );

            opportunityRepository.save(opportunity);
        }

        if (oldStatus == VolunteerApplication.Status.ACCEPTED
                && newStatus != VolunteerApplication.Status.ACCEPTED) {

            VolunteerOpportunity opportunity =
                    application.getOpportunity();

            if (opportunity.getFilledSlots() > 0) {
                opportunity.setFilledSlots(
                        opportunity.getFilledSlots() - 1
                );
            }

            opportunityRepository.save(opportunity);
        }

        VolunteerApplication saved =
                applicationRepository.save(application);

        return convertApplicationToResponse(saved);
    }

    private VolunteerOpportunityResponse
    convertOpportunityToResponse(
            VolunteerOpportunity opportunity) {

        return new VolunteerOpportunityResponse(
                opportunity.getId(),
                opportunity.getEvent().getId(),
                opportunity.getEvent().getTitle(),
                opportunity.getRole(),
                opportunity.getTeam(),
                opportunity.getRequirements(),
                opportunity.getRewardXp(),
                opportunity.getApplicationDeadline(),
                opportunity.getTotalSlots(),
                opportunity.getFilledSlots(),
                opportunity.isActive()
        );
    }

    private VolunteerApplicationResponse
    convertApplicationToResponse(
            VolunteerApplication application) {

        User user = application.getUser();
        VolunteerOpportunity opportunity =
                application.getOpportunity();

        return new VolunteerApplicationResponse(
                application.getId(),
                opportunity.getId(),
                opportunity.getEvent().getId(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                opportunity.getRole(),
                opportunity.getTeam(),
                application.getApplicationMessage(),
                application.getStatus().name(),
                application.getAppliedAt()
        );
    }
}