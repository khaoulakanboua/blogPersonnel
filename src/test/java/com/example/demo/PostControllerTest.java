package com.example.demo;

import com.example.demo.controller.PostController;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.repos.PostRepository;
import com.example.demo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;
    
    @Mock
    private PostRepository postRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveWithNonNullAttributes() {
        Post postToUpdate = new Post();
        postToUpdate.setTitle("Updated Title");
        postToUpdate.setDescription("Updated Description");
        postToUpdate.setImage("Updated Image");
        postToUpdate.setTag("Updated Tag");
        int postId = 1;
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Existing Title");
        existingPost.setDescription("Existing Description");
        existingPost.setImage("Existing Image");
        existingPost.setTag("Existing Tag");
        when(postService.findById(postId)).thenReturn(existingPost);
        when(postService.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Post result = postController.save(postToUpdate, postId);
        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Updated Image", result.getImage());
        assertEquals("Updated Tag", result.getTag());
        verify(postService, times(1)).findById(postId);
        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    void testSaveWithNullAttributes() {
        Post postToUpdate = new Post();
        postToUpdate.setId(1);
        int postId = 1;
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Existing Title");
        existingPost.setDescription("Existing Description");
        existingPost.setImage("Existing Image");
        existingPost.setTag("Existing Tag");
        when(postService.findById(postId)).thenReturn(existingPost);
        when(postService.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Post result = postController.save(postToUpdate, postId);
        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Existing Title", result.getTitle());
        assertEquals("Existing Description", result.getDescription());
        assertEquals("Existing Image", result.getImage());
        assertEquals("Existing Tag", result.getTag());
        verify(postService, times(1)).findById(postId);
        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    void testSaveWithNonExistingPost() {
        Post postToUpdate = new Post();
        postToUpdate.setTitle("Updated Title");
        postToUpdate.setDescription("Updated Description");
        postToUpdate.setImage("Updated Image");
        postToUpdate.setTag("Updated Tag");
        int nonExistingPostId = 2;
        when(postService.findById(nonExistingPostId)).thenReturn(null);
        Post result = postController.save(postToUpdate, nonExistingPostId);
        assertNull(result);
        verify(postService, times(1)).findById(nonExistingPostId);
        verify(postService, never()).save(any(Post.class));
    }



    @Test
    void testSavePostNotFound() {

        int nonExistingPostId = 2;

        when(postService.findById(nonExistingPostId)).thenReturn(null);


        Post result = postController.save(new Post(), nonExistingPostId);

        assertNull(result);

        verify(postService, times(1)).findById(nonExistingPostId);

        verify(postService, never()).save(any(Post.class));
    }

    @Test
    void testSave() throws UserPrincipalNotFoundException {

        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setImage(null);
        mockPost.setDatePub(new Date());
        mockPost.setDescription("description test");
        mockPost.setTag("tag test");
        mockPost.setTitle("title test");
        mockPost.setUserId(1L);

        User mockUser = new User();
        mockUser.setId(1L);
        when(restTemplate.getForObject(anyString(), eq(User.class))).thenReturn(mockUser);

        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        Post result = postController.save(mockPost, "username");

        assertNotNull("The result of postController.save should not be null.", String.valueOf(result));
        if (result != null) {
            assertNotNull("The ID of the result should not be null.", String.valueOf(result.getId()));
            assertEquals(1, result.getId());
        }
    }

    @Test
    void testSaveWithNullDatePub() throws UserPrincipalNotFoundException {
        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setImage(null);
        mockPost.setDatePub(null);
        mockPost.setDescription("description test");
        mockPost.setTag("tag test");
        mockPost.setTitle("title test");
        mockPost.setUserId(1L);


        User mockUser = new User();
        mockUser.setId(1L);
        when(restTemplate.getForObject(anyString(), eq(User.class))).thenReturn(mockUser);
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        Post result = postController.save(mockPost, "username");

        assertNotNull("The result of postController.save should not be null.", String.valueOf(result));
        if (result != null) {
            assertNotNull("The ID of the result should not be null.", String.valueOf(result.getId()));
            assertEquals(1, result.getId());
            assertNotNull("The DatePub of the result should not be null.", String.valueOf(result.getDatePub()));
        }
    }


    @Test
    void testSaveUserNotFound() {
        Post postToSave = new Post();
        postToSave.setTitle("Test Title");


        when(restTemplate.getForObject(anyString(), eq(User.class))).thenReturn(null);

        assertThrows(UserPrincipalNotFoundException.class, () -> {
            postController.save(postToSave, "nonexistentUser");
        });

        verify(postService, never()).save(any(Post.class));
    }

    @Test
    void testSaveUserFound() throws UserPrincipalNotFoundException {
        Post postToSave = new Post();
        postToSave.setTitle("Test Title");

        User mockUser = new User();
        mockUser.setFirstName("Existing First Name");
        mockUser.setLastName("Existing Last Name");
        mockUser.setUsername("existingUser");
        mockUser.setPassword("Existing Password");

        when(restTemplate.getForObject(anyString(), eq(User.class))).thenReturn(mockUser);

        when(postService.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postController.save(postToSave, "existingUser");

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());

        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    void testFindAll() {

        Post post1 = new Post();
        post1.setId(1);
        Post post2 = new Post();
        post2.setId(2);
        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(postService.findAll()).thenReturn(mockPosts);

        List<Post> result = postController.findAll();

        assertEquals(2, result.size());
        verify(postService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Post mockPost = new Post();
        mockPost.setId(1);

        when(postService.findById(anyInt())).thenReturn(mockPost);

        Post result = postController.findById(1);

        assertEquals(1, result.getId());
        verify(postService, times(1)).findById(anyInt());
    }

    @Test
    void testDeleteById() {
        postController.deleteById(1);

        verify(postService, times(1)).deleteById(1);
    }


    @Test
    void testFindTop10ByOrderByDatePubDesc() {
        Post post1 = new Post();
        post1.setId(1);
        Post post2 = new Post();
        post2.setId(2);
        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(postService.findTop10ByOrderByDatePubDesc()).thenReturn(mockPosts);

        List<Post> result = postController.findTop10ByOrderByDatePubDesc();

        assertEquals(2, result.size());
        verify(postService, times(1)).findTop10ByOrderByDatePubDesc();
    }

    @Test
    void testFindByUserId() {
        Post post1 = new Post();
        post1.setId(1);
        Post post2 = new Post();
        post2.setId(2);
        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(postService.findByUserId(anyLong())).thenReturn(mockPosts);

        List<Post> result = postController.findPostByUserId(1L);

        assertEquals(2, result.size());
        verify(postService, times(1)).findByUserId(anyLong());
    }

    @Test
    void testFindCommentByPost() {
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setPostId(1L);
        comment1.setUserId(1L);
        comment1.setContent("test content");
        comment1.setDatePub(new Date());
        Comment comment2 = new Comment();
        comment2.setId(2);
        Comment[] mockComments = {comment1, comment2};

        when(restTemplate.getForEntity(anyString(), eq(Comment[].class))).thenReturn(ResponseEntity.ok(mockComments));

        Comment[] result = postController.findCommentByPost(1L);

        result[0].getContent();
        result[0].getPostId();
        result[0].getUserId();
        result[0].getId();
        result[0].getDatePub();
        assertEquals(2, result.length);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(Comment[].class));
    }


    @Test
    void testUpdate() {
        int postId = 1;
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Existing Title");
        existingPost.setDescription("Existing Description");
        existingPost.setImage("Existing Image");
        existingPost.setTag("Existing Tag");
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setDescription("Updated Description");
        updatedPost.setImage("Updated Image");
        updatedPost.setTag("Updated Tag");
        when(postService.findById(postId)).thenReturn(existingPost);
        when(postService.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Post result = postController.save(updatedPost, postId);
        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Updated Image", result.getImage());
        assertEquals("Updated Tag", result.getTag());

        verify(postService, times(1)).findById(postId);
        verify(postService, times(1)).save(any(Post.class));
    }
}
