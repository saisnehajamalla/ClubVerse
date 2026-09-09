package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.PostRequest;
import com.clubverse.clubverse_backend.dto.PostResponse;
import com.clubverse.clubverse_backend.service.PostService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody PostRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        PostResponse response = postService.createPost(request, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {

        return ResponseEntity.ok(postService.getAllPosts());
    }
}