package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    /**
     * Find badge by name
     */
    Optional<Badge> findByName(String name);
}
