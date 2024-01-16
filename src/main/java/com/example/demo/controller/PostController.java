package com.example.demo.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import com.example.demo.entities.Comment;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Post;
import com.example.demo.service.PostService;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/post")
public class PostController {
	private final PostService postService;
	private final RestTemplate restTemplate;

	@Autowired
	public PostController(PostService postService, RestTemplate restTemplate) {
		this.postService = postService;
		this.restTemplate = restTemplate;
	}


	String uri = "http://localhost:8060/api/test";
	String uri2 = "http://localhost:8010/comment";


	@PostMapping("/save/{username}")
	public Post save(@RequestBody Post p, @PathVariable String username) throws UserPrincipalNotFoundException {
		String url = UriComponentsBuilder.fromUriString(this.uri)
				.path("/findbyusername/{username}")
				.buildAndExpand(username)
				.toUriString();

		User user = restTemplate.getForObject(url, User.class);

			p.setUserId(user.getId());
		return postService.save(p);
	}

	@GetMapping("/all")
	public List<Post> findAll() {
		return postService.findAll();
	}

	@GetMapping("/findbyid/{id}")
	public Post findById(@PathVariable Integer id) {
		return postService.findById(id);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Integer id) {
		postService.deleteById(id);
	}
	
	@PutMapping("/update/{id}")
	public Post save(@RequestBody Post p,@PathVariable int id) {
		 Post post = postService.findById(id);
	        if (post != null) {
	        	 if (p.getTitle() != null) {
	 	            post.setTitle(p.getTitle());

	             }
	        	 if (p.getDescription() != null) {
	            post.setDescription(p.getDescription());
	        	 }
	        	 if(p.getImage() != null) {
	        		 post.setImage(p.getImage());
	        	 }
	        	 if(p.getTag() != null) {
	        		 post.setTag(p.getTag());
	        	 }
	            return postService.save(post);
	        }
	        return null;	}

	@GetMapping("/getLast")
	public List<Post> findTop10ByOrderByDatePubDesc() {
		return postService.findTop10ByOrderByDatePubDesc();
	}



	@GetMapping("/findbyuserid/{id}")
	public List<Post> findPostByUserId(@PathVariable Long id) {
		return postService.findByUserId(id);
	}

	@GetMapping("/comment/{id}")
	public Comment[] findCommentByPost(@PathVariable Long id) {

		ResponseEntity<Comment[]> commentList = restTemplate.getForEntity(this.uri2+"/findcomment/id/"+id, Comment[].class);
		return commentList.getBody();
	}

}
