package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import id.ac.ui.cs.advprog.gametime.service.ReviewFilterService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private FilterService filterService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private UserService userService;

    @Mock
    private ReviewFilterService reviewFilterService;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void testHome() throws Exception {
        List<Game> topThreeFreeGames = Arrays.asList(new Game(), new Game(), new Game());
        List<Game> topSixGamesByRating = Arrays.asList(new Game(), new Game(), new Game(), new Game(), new Game(), new Game());
        List<Review> topSixReviews = Arrays.asList(new Review(), new Review(), new Review(), new Review(), new Review(), new Review());
        User loggedInUser = new User(); // Replace with actual User object if available

        when(filterService.getTopThreeFreeGames()).thenReturn(topThreeFreeGames);
        when(filterService.getGamesTop6OrderByRating()).thenReturn(topSixGamesByRating);
        when(reviewFilterService.findTop6ByRatingGreaterThanEqual(4)).thenReturn(topSixReviews);
        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("games", topThreeFreeGames))
                .andExpect(model().attribute("gamesTop6", topSixGamesByRating))
                .andExpect(model().attribute("reviewService", reviewService))
                .andExpect(model().attribute("reviews", topSixReviews))
                .andExpect(model().attribute("user", loggedInUser)); // Replace with actual User object if available

        verify(filterService, times(1)).getTopThreeFreeGames();
        verify(filterService, times(1)).getGamesTop6OrderByRating();
        verify(reviewFilterService, times(1)).findTop6ByRatingGreaterThanEqual(4);
        verify(userService, times(1)).getLoggedInUser();
    }
}