package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}