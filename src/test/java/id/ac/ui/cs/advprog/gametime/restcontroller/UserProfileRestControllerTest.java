package id.ac.ui.cs.advprog.gametime.restcontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.CategoryRepository;
import id.ac.ui.cs.advprog.gametime.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserProfileRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UserProfileRestController userProfileRestController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileRestController).build();
    }

    @Test
    public void testGetUserProfile() throws Exception {
        User user = new User.Builder()
                .username("test")
                .password("test")
                .bio("test")
                .isAdmin(true)
                .email("test@test.com")
                .build();

        when(userService.findByUsername("test")).thenReturn(user);
        mockMvc.perform(get("/api/profile/test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("test")))
                .andExpect(jsonPath("$.bio", is("test")))
                .andExpect(jsonPath("$.admin", is(true)))
                .andExpect(jsonPath("$.email", is("test@test.com")));
    }

}
