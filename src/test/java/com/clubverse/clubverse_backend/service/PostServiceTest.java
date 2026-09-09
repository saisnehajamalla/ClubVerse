package com.clubverse.clubverse_backend.service;

import com.clubverse.clubverse_backend.dto.PostRequest;
import com.clubverse.clubverse_backend.entity.ModerationDecision;
import com.clubverse.clubverse_backend.entity.ModerationResult;
import com.clubverse.clubverse_backend.entity.Post;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.ModerationResultRepository;
import com.clubverse.clubverse_backend.repository.PostRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModerationService moderationService;

    @Mock
    private ModerationResultRepository moderationResultRepository;

    @Test
    void approvesAndPersistsModeratedPost() {
        User user = new User();
        when(userRepository.findByEmail("student@example.com"))
                .thenReturn(Optional.of(user));
        when(moderationService.moderate("clean content"))
                .thenReturn(result(ModerationDecision.APPROVE));
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostService postService = newPostService();
        postService.createPost(request("clean content"), "student@example.com");

        verify(moderationService).moderate("clean content");
        verify(moderationResultRepository).save(any(ModerationResult.class));
    }

    @Test
    void rejectsAndPersistsModerationResult() {
        User user = new User();
        when(userRepository.findByEmail("student@example.com"))
                .thenReturn(Optional.of(user));
        when(moderationService.moderate("harmful content"))
                .thenReturn(result(ModerationDecision.REJECT));
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostService postService = newPostService();

        assertThrows(ModerationRejectedException.class,
                () -> postService.createPost(request("harmful content"), "student@example.com"));

        verify(moderationResultRepository).save(any(ModerationResult.class));
    }

    private PostService newPostService() {
        return new PostService(
                postRepository,
                userRepository,
                moderationService,
                moderationResultRepository
        );
    }

    private PostRequest request(String content) {
        PostRequest request = new PostRequest();
        request.setCommunity("general");
        request.setTitle("Test post");
        request.setContent(content);
        return request;
    }

    private ModerationResult result(ModerationDecision decision) {
        ModerationResult result = new ModerationResult();
        result.setDecision(decision);
        result.setProvider("test-provider");
        return result;
    }
}