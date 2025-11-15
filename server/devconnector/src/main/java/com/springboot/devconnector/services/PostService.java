package com.springboot.devconnector.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.devconnector.dto.comment.CommentRequest;
import com.springboot.devconnector.dto.comment.CommentResponse;
import com.springboot.devconnector.dto.like.LikeResponse;
import com.springboot.devconnector.dto.post.PostRequest;
import com.springboot.devconnector.dto.post.PostResponse;
import com.springboot.devconnector.models.Comment;
import com.springboot.devconnector.models.Like;
import com.springboot.devconnector.models.Post;
import com.springboot.devconnector.models.User;
import com.springboot.devconnector.repositories.PostRepository;
import com.springboot.devconnector.repositories.UserRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByDateDesc()
                .stream()
                .map(this::postToResponse)
                .collect(Collectors.toList());
    }

    public PostResponse createPost(String userEmail, PostRequest request) {
        User user = findUserByEmail(userEmail);
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);
        post.setName(user.getName());
        post.setAvatar(user.getAvatar());
        post.setDate(LocalDateTime.now());
        postRepository.save(post);
        return postToResponse(post);
    }

    public void deletePost(String postId, String userEmail) {
        Post post = getPostOrThrow(postId);
        if (post.getUser() == null || !post.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("User not authorized");
        }
        postRepository.delete(post);
    }

    public List<LikeResponse> likePost(String postId, String userEmail) {
        Post post = getPostOrThrow(postId);
        User user = findUserByEmail(userEmail);

        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUser() != null && like.getUser().getId().equals(user.getId()));
        if (alreadyLiked) {
            throw new RuntimeException("Post already liked");
        }

        post.getLikes().add(0, new Like(user));
        postRepository.save(post);
        return mapLikes(post.getLikes());
    }

    public List<LikeResponse> unlikePost(String postId, String userEmail) {
        Post post = getPostOrThrow(postId);
        boolean removed = post.getLikes().removeIf(
                like -> like.getUser() != null && like.getUser().getEmail().equals(userEmail));
        if (!removed) {
            throw new RuntimeException("Post has not yet been liked");
        }

        postRepository.save(post);
        return mapLikes(post.getLikes());
    }

    public PostResponse getPost(String postId) {
        Post post = getPostOrThrow(postId);
        return postToResponse(post);
    }

    public List<CommentResponse> addComment(String postId, String userEmail, CommentRequest request) {
        Post post = getPostOrThrow(postId);
        User user = findUserByEmail(userEmail);

        Comment comment = new Comment(new ObjectId(), user, request.getText(), user.getName(), user.getAvatar(),
                LocalDateTime.now());
        post.getComments().add(0, comment);
        postRepository.save(post);
        return mapComments(post.getComments());
    }

    public List<CommentResponse> deleteComment(String postId, String commentId, String userEmail) {
        Post post = getPostOrThrow(postId);
        Comment comment = post.getComments().stream()
                .filter(c -> c.getId() != null && c.getId().toHexString().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Comment does not exist"));

        if (comment.getUser() == null || !comment.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("User not authorized");
        }

        post.getComments().remove(comment);
        postRepository.save(post);
        return mapComments(post.getComments());
    }

    private PostResponse postToResponse(Post post) {
        return new PostResponse(post.getId(), post.getText(), post.getName(), post.getAvatar(),
                post.getUser() != null ? post.getUser().getId() : null, mapLikes(post.getLikes()),
                mapComments(post.getComments()), post.getDate());
    }

    private List<LikeResponse> mapLikes(List<Like> likes) {
        if (likes == null) {
            return Collections.emptyList();
        }
        return likes.stream()
                .map(like -> new LikeResponse(like.getId() != null ? like.getId().toHexString() : null,
                        like.getUser() != null ? like.getUser().getId() : null))
                .collect(Collectors.toList());
    }

    private List<CommentResponse> mapComments(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream()
                .map(comment -> new CommentResponse(comment.getId() != null ? comment.getId().toHexString() : null,
                        comment.getText(), comment.getName(), comment.getAvatar(),
                        comment.getUser() != null ? comment.getUser().getId() : null, comment.getDate()))
                .collect(Collectors.toList());
    }

    private Post getPostOrThrow(String postId) {
        if (postId == null || postId.isEmpty()) {
            throw new RuntimeException("Post ID is required");
        }
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
