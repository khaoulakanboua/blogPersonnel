package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Post;
import com.example.demo.repos.PostRepository;

@Service
public class PostService {
	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post save(Post p) {

		if (p.getDatePub() == null) {
			p.setDatePub(new Date());
		}
		return postRepository.save(p);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public Post findById(Integer id) {

		Optional<Post> optionalPost = postRepository.findById(id);
		return optionalPost.orElse(null);
	}

	public void deleteById(Integer id) {
		postRepository.deleteById(id);
	}

	public List<Post> findTop10ByOrderByDatePubDesc() {
		return postRepository.findTop10ByOrderByDatePubDesc();
	}

	public List<Post> findByUserId(Long id) {
		return postRepository.findByUserId(id);
	}
}
