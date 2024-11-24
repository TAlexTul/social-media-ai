package com.epam.socialmedia.service;

import com.epam.socialmedia.entity.Post;
import com.epam.socialmedia.entity.User;
import com.epam.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post, User author) {
        post.setAuthor(author);
        return postRepository.save(post);
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public Post updatePost(Long id, Post updatedPost) {
        Post existingPost = getPostById(id);
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setBody(updatedPost.getBody());
        return postRepository.save(existingPost);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }
}
