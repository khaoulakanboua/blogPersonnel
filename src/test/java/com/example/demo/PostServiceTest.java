package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.Post;
import com.example.demo.repos.PostRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testSave() {
        Post postToSave = new Post();
        postToSave.setTitle("Test Title");
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1);
            return savedPost;
        });

        Post savedPost = postService.save(postToSave);
        assertNotEquals(0, savedPost.getId(), "ID should not be zero");
        assertNotNull(savedPost.getTitle());
        assertEquals("Test Title", savedPost.getTitle());
        assertNotNull(savedPost.getDatePub());
        assertNotNull(savedPost.getDatePub());
        verify(postRepository, times(1)).save(any(Post.class));
    }
    @Test
    void givenNewPost_whenSave_thenPostIsSavedWithGeneratedId() {
        Post postToSave = new Post();
        postToSave.setTitle("Test Title");
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1);
            return savedPost;
        });
        Post savedPost = postService.save(postToSave);
        assertNotEquals(0, savedPost.getId());
        assertEquals("Test Title", savedPost.getTitle());
        assertNotNull(savedPost.getDatePub());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testFindAll() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(new Post(), new Post()));
        List<Post> posts = postService.findAll();
        assertEquals(2, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        int postId = 1;
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post(postId, "Test Title", "Test Description", "Test Image", "Test Tag", new Date(), 1L)));
        Post foundPost = postService.findById(postId);
        assertNotNull(foundPost);
        assertEquals("Test Title", foundPost.getTitle());
        assertEquals("Test Description", foundPost.getDescription());
        assertEquals("Test Image", foundPost.getImage());
        assertEquals("Test Tag", foundPost.getTag());
        assertNotNull(foundPost.getDatePub());
        assertEquals(1L, foundPost.getUserId());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void testDeleteById() {
        int postId = 1;
        postService.deleteById(postId);
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    void testFindTop10ByOrderByDatePubDesc() {
        when(postRepository.findTop10ByOrderByDatePubDesc()).thenReturn(Arrays.asList(new Post(), new Post()));
        List<Post> posts = postService.findTop10ByOrderByDatePubDesc();
        assertEquals(2, posts.size());
        verify(postRepository, times(1)).findTop10ByOrderByDatePubDesc();
    }

    @Test
    void testFindByUserId() {
        long userId = 1L;
        when(postRepository.findByUserId(userId)).thenReturn(Arrays.asList(new Post(), new Post()));
        List<Post> userPosts = postService.findByUserId(userId);
        assertEquals(2, userPosts.size());
        verify(postRepository, times(1)).findByUserId(userId);
    }
    @Test
    void testSaveWithNonNullDatePub() {
        Post postToSave = new Post();
        postToSave.setTitle("Test Title");
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1);
            return savedPost;
        });

        Post savedPost = postService.save(postToSave);
        assertNotEquals(0, savedPost.getId(), "ID should not be zero");
        assertNotNull(savedPost.getTitle());
        assertEquals("Test Title", savedPost.getTitle());
        assertNotNull(savedPost.getDatePub());
        verify(postRepository, times(1)).save(any(Post.class));
    }

}
