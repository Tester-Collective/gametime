package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.facade.HomeFacade;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
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
    private HomeFacade homeFacade;

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

        when(homeFacade.getTopThreeFreeGames()).thenReturn(topThreeFreeGames);
        when(homeFacade.getGamesTop6OrderByRating()).thenReturn(topSixGamesByRating);
        when(homeFacade.getTop6Reviews()).thenReturn(topSixReviews);
        when(homeFacade.getLoggedInUser()).thenReturn(loggedInUser);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("games", topThreeFreeGames))
                .andExpect(model().attribute("gamesTop6", topSixGamesByRating))
                .andExpect(model().attribute("reviews", topSixReviews))
                .andExpect(model().attribute("user", loggedInUser));

        verify(homeFacade, times(1)).getTopThreeFreeGames();
        verify(homeFacade, times(1)).getGamesTop6OrderByRating();
        verify(homeFacade, times(1)).getTop6Reviews();
        verify(homeFacade, times(1)).getLoggedInUser();
    }
}
