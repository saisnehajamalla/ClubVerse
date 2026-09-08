package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.PostRequest;
import com.clubverse.clubverse_backend.dto.PostResponse;
import com.clubverse.clubverse_backend.entity.Post;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.PostRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponse createPost(PostRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();

        post.setCommunity(request.getCommunity());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setMediaUrl(request.getMediaUrl());
        post.setAnonymous(request.isAnonymous());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return convertToResponse(savedPost);
    }

    public List<PostResponse> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private PostResponse convertToResponse(Post post) {

        String userName = post.isAnonymous()
                ? "Anonymous"
                : post.getUser().getName();

        return new PostResponse(
                post.getId(),
                post.getCommunity(),
                post.getTitle(),
                post.getContent(),
                post.getMediaUrl(),
                post.isAnonymous(),
                post.getLikeCount(),
                post.getCreatedAt(),
                post.getUser().getId(),
                userName
        );
    }
}