package com.epam.socialmedia.service;

import com.epam.socialmedia.entity.User;
import com.epam.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("john_doe");
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("john_doe", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        // Given: A mock User object with id = 1L
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");

        // Mock the behavior of the userRepository to return the user when findById is called
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When: Call the service method to get the user by id
        Optional<User> fetchedUser = userService.getUserById(1L);

        // Then: Assert that the fetched user is present and has the expected id
        assertTrue(fetchedUser.isPresent());
        assertEquals(1L, fetchedUser.get().getId());
        assertEquals("john_doe", fetchedUser.get().getUsername());
    }
}
