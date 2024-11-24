package com.epam.socialmedia.controller;

import com.epam.socialmedia.entity.User;
import com.epam.socialmedia.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Manually create mocks and the controller
        userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        // Initialize MockMvc with standaloneSetup
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        // Given: Mock a User object to be created
        User user = new User();
        user.setUsername("new_user");

        // Mock the service call
        when(userService.createUser(any(User.class))).thenReturn(user);

        // When: Perform POST request with JSON payload
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"new_user\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("new_user"));

        // Verify the service method was called
        verify(userService, times(1)).createUser(any(User.class));
    }
}
