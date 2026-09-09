package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.CommentRequest;
import com.clubverse.clubverse_backend.dto.CommentResponse;
import com.clubverse.clubverse_backend.entity.Comment;
import com.clubverse.clubverse_backend.entity.Post;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.CommentRepository;
import com.clubverse.clubverse_backend.repository.PostRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentResponse createComment(
            Long postId,
            CommentRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setAnonymous(request.isAnonymous());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);

        if (request.getParentCommentId() != null) {
            Comment parentComment = commentRepository
                    .findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));

            comment.setParentComment(parentComment);
        }

        Comment savedComment = commentRepository.save(comment);

        return convertToResponse(savedComment);
    }

    public List<CommentResponse> getComments(Long postId) {

        return commentRepository.findByPostIdAndParentCommentIsNull(postId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private CommentResponse convertToResponse(Comment comment) {

        String userName = comment.isAnonymous()
                ? "Anonymous"
                : comment.getUser().getName();

        List<CommentResponse> replies =
                commentRepository.findByParentCommentId(comment.getId())
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        Long parentCommentId = comment.getParentComment() != null
                ? comment.getParentComment().getId()
                : null;

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.isAnonymous(),
                comment.getLikeCount(),
                comment.getCreatedAt(),
                comment.getUser().getId(),
                userName,
                parentCommentId,
                replies
        );
    }
}