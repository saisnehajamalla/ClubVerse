package com.clubverse.clubverse_backend.controller;

import com.clubverse.clubverse_backend.dto.CommentRequest;
import com.clubverse.clubverse_backend.dto.CommentResponse;
import com.clubverse.clubverse_backend.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        CommentResponse response =
                commentService.createComment(postId, request, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId) {

        return ResponseEntity.ok(
                commentService.getComments(postId)
        );
    }
}