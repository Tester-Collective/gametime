package id.ac.ui.cs.advprog.gametime.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.ImageService;
import id.ac.ui.cs.advprog.gametime.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private UserProfileController userProfileController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
    }

    @Test
    public void testGetUserProfile() throws Exception {
        User user = new User.Builder()
                .username("test")
                .build();

        when(userService.findByUsername("test")).thenReturn(user);
        when(userService.getLoggedInUser()).thenReturn(user);
        mockMvc.perform(get("/profile/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile/view"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("can_edit", true));
    }
}
