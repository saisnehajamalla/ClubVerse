package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.VolunteerOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerOpportunityRepository
        extends JpaRepository<VolunteerOpportunity, Long> {

    List<VolunteerOpportunity> findByEventId(Long eventId);

    List<VolunteerOpportunity> findByActiveTrue();
}