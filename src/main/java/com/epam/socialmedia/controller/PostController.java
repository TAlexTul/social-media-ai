package com.epam.socialmedia.controller;

import com.epam.socialmedia.entity.Post;
import com.epam.socialmedia.entity.User;
import com.epam.socialmedia.service.PostService;
import com.epam.socialmedia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam Long authorId) {
        User author = userService.getUserById(authorId).orElseThrow();
        Post createdPost = postService.createPost(post, author);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Long authorId) {
        return postService.getPostsByAuthor(authorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(id, updatedPost);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
