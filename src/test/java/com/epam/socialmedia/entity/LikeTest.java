package com.epam.socialmedia.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LikeTest {

    @Test
    public void testLikeEntity() {
        Like like = new Like();
        User user = new User();
        user.setUsername("john_doe");
        Post post = new Post();
        post.setTitle("Test Post");

        like.setUser(user);
        like.setPost(post);

        assertNotNull(like.getUser());
        assertEquals("john_doe", like.getUser().getUsername());
        assertNotNull(like.getPost());
        assertEquals("Test Post", like.getPost().getTitle());
    }
}
