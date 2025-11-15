package com.springboot.devconnector.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springboot.devconnector.models.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByOrderByDateDesc();
}
