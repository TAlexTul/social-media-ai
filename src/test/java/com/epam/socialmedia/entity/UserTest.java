package com.epam.socialmedia.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserEntity() {
        User user = new User();
        user.setUsername("john_doe");

        assertNotNull(user.getUsername());
        assertEquals("john_doe", user.getUsername());
    }
}
