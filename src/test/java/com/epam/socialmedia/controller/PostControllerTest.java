package com.epam.socialmedia.controller;

import com.epam.socialmedia.entity.Post;
import com.epam.socialmedia.entity.User;
import com.epam.socialmedia.service.PostService;
import com.epam.socialmedia.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest {

    private PostService postService;
    private UserService userService;
    private MockMvc mockMvc;

    private Post post;
    private User user;

    @BeforeEach
    public void setUp() {
        postService = Mockito.mock(PostService.class); // Mock PostService
        userService = Mockito.mock(UserService.class); // Mock UserService
        PostController postController = new PostController(postService, userService); // Initialize controller with mocks

        // Manually set up MockMvc using MockMvcBuilders
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        // Prepare test data
        post = new Post();
        post.setTitle("Test Post");
        post.setBody("This is a test post.");

        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
    }

    @Test
    public void testCreatePost() throws Exception {
        // Given: Mock the behavior of the userService and postService
        when(userService.getUserById(eq(1L))).thenReturn(java.util.Optional.of(user));
        when(postService.createPost(any(Post.class), eq(user))).thenReturn(post);

        // When: Performing the POST request to create a post
        mockMvc.perform(post("/posts")
                        .contentType("application/json")
                        .content("{\"title\": \"Test Post\", \"body\": \"This is a test post.\"}")
                        .param("authorId", "1"))
                .andExpect(status().isCreated())  // Expecting HTTP status 201
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.body").value("This is a test post."));
    }

    @Test
    public void testGetAllPosts() throws Exception {
        // Given: Mock the behavior of the postService
        when(postService.getPostsByAuthor(eq(1L))).thenReturn(java.util.List.of(post));

        // When: Performing the GET request to retrieve posts by authorId
        mockMvc.perform(get("/posts")
                        .param("authorId", "1"))
                .andExpect(status().isOk())  // Expecting HTTP status 200
                .andExpect(jsonPath("$[0].title").value("Test Post"))
                .andExpect(jsonPath("$[0].body").value("This is a test post."));
    }

    @Test
    public void testGetPostById() throws Exception {
        when(postService.getPostById(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.body").value("This is a test post."));
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated Body");

        when(postService.updatePost(eq(1L), any(Post.class))).thenReturn(updatedPost);

        mockMvc.perform(put("/posts/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Updated Title\", \"body\": \"Updated Body\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.body").value("Updated Body"));
    }

    @Test
    public void testDeletePost() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }
}
