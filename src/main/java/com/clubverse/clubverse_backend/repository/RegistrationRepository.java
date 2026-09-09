package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository
        extends JpaRepository<Registration, Long> {

    Optional<Registration> findByEventIdAndUserId(
            Long eventId,
            Long userId);

    List<Registration> findByEventId(Long eventId);

    List<Registration> findByUserId(Long userId);
}