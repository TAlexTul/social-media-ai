package com.epam.socialmedia.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    public void testPostEntity() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setBody("This is a test post.");

        assertNotNull(post.getTitle());
        assertEquals("Test Post", post.getTitle());
        assertNotNull(post.getBody());
        assertEquals("This is a test post.", post.getBody());
    }
}
