package com.clubverse.clubverse_backend.repository;

import com.clubverse.clubverse_backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}