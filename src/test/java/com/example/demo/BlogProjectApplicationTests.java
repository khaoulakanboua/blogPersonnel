package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.Post;
import com.example.demo.repos.PostRepository;
import com.example.demo.service.PostService;

import jakarta.transaction.Transactional;

@SpringBootTest
class BlogProjectApplicationTests {

	@Test
	void contextLoads() {
	}
	
	 @Autowired
	    private PostRepository postRepository;

	    @Autowired
	    private PostService postService;

	    @Test
	    @Transactional
	    void testSave() {
	        // Given
	        Post post = new Post();
	        post.setTitle("Test Title");
	      //  post.setContent("Test Content");

	        // When
	        Post savedPost = postService.save(post);

	        // Then
	        assertNotNull(savedPost.getId());
	        assertEquals("Test Title", savedPost.getTitle());
	       // assertEquals("Test Content", savedPost.getContent());
	        assertNotNull(savedPost.getDatePub());
	    }


}
