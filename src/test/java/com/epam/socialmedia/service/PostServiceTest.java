package com.epam.socialmedia.service;

import com.epam.socialmedia.entity.Post;
import com.epam.socialmedia.entity.User;
import com.epam.socialmedia.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setBody("This is a test post.");
    }

    @Test
    public void testCreatePost() {
        // Given: A mock User object and a mock Post object
        User author = new User();
        author.setId(1L);  // Set the id for the author
        author.setUsername("john_doe");

        // Mocking the save method to return the same post object
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When: Calling the createPost method with the mock post and author
        Post createdPost = postService.createPost(post, author);

        // Then: Verifying the created post is not null and has correct title
        assertNotNull(createdPost);
        assertEquals("Test Post", createdPost.getTitle());
        assertEquals("This is a test post.", createdPost.getBody());
        assertEquals(1L, createdPost.getAuthor().getId());  // Verifying the author's ID is set

        // Verifying that the save method was called exactly once
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testGetPostsByAuthor() {
        // Given: A mock User object with id = 1L
        User author = new User();
        author.setId(1L);
        author.setUsername("john_doe");

        // Given: A mock Post object with a title, body, and associated author
        post.setAuthor(author);  // Set the author for the post

        // Given: A list of posts that includes the created post
        List<Post> posts = List.of(post);

        // Mock the behavior of the postRepository to return the list of posts for the given author
        when(postRepository.findByAuthorId(1L)).thenReturn(posts);

        // When: Call the service method to get the posts by author
        List<Post> fetchedPosts = postService.getPostsByAuthor(1L);

        // Then: Assert that the fetched posts list is not empty and contains the correct post
        assertNotNull(fetchedPosts);
        assertEquals(1, fetchedPosts.size());
        assertEquals("Test Post", fetchedPosts.get(0).getTitle());
        assertEquals("john_doe", fetchedPosts.get(0).getAuthor().getUsername()); // Verify the author is correct
    }

    // 1. Test getPostById
    @Test
    void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals("Test Post", result.getTitle());
        assertEquals("This is a test post.", result.getBody());

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPostById_NotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> postService.getPostById(1L));

        assertEquals("Post not found with id: 1", exception.getMessage());
        verify(postRepository, times(1)).findById(1L);
    }

    // 2. Test updatePost
    @Test
    void testUpdatePost() {
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated Body");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postService.updatePost(1L, updatedPost);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Body", result.getBody());
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(post);
    }

    // 3. Test deletePost
    @Test
    void testDeletePost() {
        when(postRepository.existsById(1L)).thenReturn(true);
        doNothing().when(postRepository).deleteById(1L);

        assertDoesNotThrow(() -> postService.deletePost(1L));

        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePost_NotFound() {
        when(postRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> postService.deletePost(1L));

        assertEquals("Post not found with id: 1", exception.getMessage());
        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(0)).deleteById(anyLong());
    }
}
