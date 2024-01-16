package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Post;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findTop10ByOrderByDatePubDesc();

    List<Post> findByTitle(String title);

    @Query("from Post p where p.userId= ?1")
    List<Post> findByUserId(Long id);

}
