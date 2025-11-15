package com.springboot.devconnector.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.devconnector.dto.comment.CommentRequest;
import com.springboot.devconnector.dto.comment.CommentResponse;
import com.springboot.devconnector.dto.like.LikeResponse;
import com.springboot.devconnector.dto.post.PostRequest;
import com.springboot.devconnector.dto.post.PostResponse;
import com.springboot.devconnector.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // @route GET api/posts
    // @desc Get all posts
    // @access Private
    @GetMapping({ "", "/" })
    public List<PostResponse> getAllPosts(@AuthenticationPrincipal UserDetails userDetails) {
        List<PostResponse> posts = postService.getAllPosts();
        return posts;
    }

    // @route GET api/posts/:postId
    // @desc Get post by ID
    // @access Private
    @GetMapping("/{postId}")
    public PostResponse getPost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String postId) {
        PostResponse post = postService.getPost(postId);
        return post;
    }

    // @route POST api/posts
    // @desc Create a post
    // @access Private
    @PostMapping({ "", "/" })
    public ResponseEntity<PostResponse> createPost(@AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PostRequest request) {
        PostResponse postResponse = postService.createPost(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    // @route DELETE api/posts/:postId
    // @desc Delete a post
    // @access Private
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String postId) {
        postService.deletePost(postId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // @route PUT api/posts/like/:postId
    // @desc Like a post
    // @access Private
    @PutMapping("/like/{postId}")
    public List<LikeResponse> likePost(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String postId) {
        List<LikeResponse> likes = postService.likePost(postId, userDetails.getUsername());
        return likes;
    }

    // @route PUT api/posts/unlike/:postId
    // @desc Unlike a post
    // @access Private
    @PutMapping("/unlike/{postId}")
    public List<LikeResponse> unlikePost(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String postId) {
        List<LikeResponse> likes = postService.unlikePost(postId, userDetails.getUsername());
        return likes;
    }

    // @route POST api/posts/comment/:postId
    // @desc Comment on a post
    // @access Private
    @PostMapping("/comment/{postId}")
    public List<CommentResponse> addComment(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String postId, @Valid @RequestBody CommentRequest request) {
        List<CommentResponse> comments = postService.addComment(postId, userDetails.getUsername(), request);
        return comments;
    }

    // @route DELETE api/posts/comment/:postId/:commentId
    // @desc Delete comment
    // @access Private
    @DeleteMapping("/comment/{postId}/{commentId}")
    public List<CommentResponse> deleteComment(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String postId,
            @PathVariable String commentId) {
        return postService.deleteComment(postId, commentId, userDetails.getUsername());
    }



}
