package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolunteerApplicationRepository
        extends JpaRepository<VolunteerApplication, Long> {

    Optional<VolunteerApplication> findByOpportunityIdAndUserId(
            Long opportunityId,
            Long userId);

    List<VolunteerApplication> findByOpportunityId(
            Long opportunityId);

    List<VolunteerApplication> findByUserId(
            Long userId);

    List<VolunteerApplication> findByStatus(
            VolunteerApplication.Status status);
}