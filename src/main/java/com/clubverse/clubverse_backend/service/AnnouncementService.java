package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.AnnouncementRequest;
import com.clubverse.clubverse_backend.dto.AnnouncementResponse;
import com.clubverse.clubverse_backend.entity.Announcement;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.AnnouncementRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    public AnnouncementService(
            AnnouncementRepository announcementRepository,
            UserRepository userRepository) {

        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public AnnouncementResponse createAnnouncement(
            AnnouncementRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Announcement announcement = new Announcement();

        announcement.setTitle(request.getTitle());
        announcement.setMessageBody(request.getMessageBody());
        announcement.setMediaUrl(request.getMediaUrl());
        announcement.setEventId(request.getEventId());
        announcement.setCreatedBy(user.getId());

        Announcement.TargetAudience audience;

        try {
            audience = Announcement.TargetAudience.valueOf(
                    request.getTargetAudience().toUpperCase()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid target audience. Use CLUB_MEMBERS, EVENT_REGISTRANTS or ENTIRE_CAMPUS"
            );
        }

        announcement.setTargetAudience(audience);

        if (request.getScheduledAt() != null) {
            announcement.setScheduledAt(request.getScheduledAt());
            announcement.setStatus(Announcement.Status.SCHEDULED);
        } else {
            announcement.setScheduledAt(null);
            announcement.setStatus(Announcement.Status.PUBLISHED);
        }

        LocalDateTime now = LocalDateTime.now();
        announcement.setCreatedAt(now);
        announcement.setUpdatedAt(now);

        Announcement saved =
                announcementRepository.save(announcement);

        return convertToResponse(saved);
    }

    public AnnouncementResponse saveDraft(
            AnnouncementRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Announcement announcement = new Announcement();

        announcement.setTitle(request.getTitle());
        announcement.setMessageBody(request.getMessageBody());
        announcement.setMediaUrl(request.getMediaUrl());
        announcement.setEventId(request.getEventId());
        announcement.setCreatedBy(user.getId());

        Announcement.TargetAudience audience;

        try {
            audience = Announcement.TargetAudience.valueOf(
                    request.getTargetAudience().toUpperCase()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid target audience"
            );
        }

        announcement.setTargetAudience(audience);
        announcement.setScheduledAt(request.getScheduledAt());
        announcement.setStatus(Announcement.Status.DRAFT);

        LocalDateTime now = LocalDateTime.now();
        announcement.setCreatedAt(now);
        announcement.setUpdatedAt(now);

        Announcement saved =
                announcementRepository.save(announcement);

        return convertToResponse(saved);
    }

    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<AnnouncementResponse> getPublishedAnnouncements() {

        return announcementRepository
                .findByStatus(Announcement.Status.PUBLISHED)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public AnnouncementResponse getAnnouncement(Long id) {

        Announcement announcement =
                announcementRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found"));

        return convertToResponse(announcement);
    }

    public AnnouncementResponse updateStatus(
            Long id,
            String status) {

        Announcement announcement =
                announcementRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Announcement not found"));

        Announcement.Status newStatus;

        try {
            newStatus = Announcement.Status.valueOf(
                    status.toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Invalid status. Use DRAFT, SCHEDULED or PUBLISHED"
            );
        }

        announcement.setStatus(newStatus);
        announcement.setUpdatedAt(LocalDateTime.now());

        Announcement saved =
                announcementRepository.save(announcement);

        return convertToResponse(saved);
    }

    private AnnouncementResponse convertToResponse(
            Announcement announcement) {

        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getMessageBody(),
                announcement.getMediaUrl(),
                announcement.getTargetAudience().name(),
                announcement.getEventId(),
                announcement.getScheduledAt(),
                announcement.getStatus().name(),
                announcement.getCreatedBy(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt()
        );
    }
}