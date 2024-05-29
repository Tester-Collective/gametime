package id.ac.ui.cs.advprog.gametime.facade;

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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeFacadeTest {

    @Mock
    private FilterService filterService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private UserService userService;

    @Mock
    private ReviewFilterService reviewFilterService;

    @InjectMocks
    private HomeFacade homeFacade;

    private List<Game> topThreeFreeGames;
    private List<Game> topSixGamesByRating;
    private List<Review> topSixReviews;
    private User loggedInUser;

    @BeforeEach
    void setUp() {
        topThreeFreeGames = Arrays.asList(new Game(), new Game(), new Game());
        topSixGamesByRating = Arrays.asList(new Game(), new Game(), new Game(), new Game(), new Game(), new Game());
        topSixReviews = Arrays.asList(new Review(), new Review(), new Review(), new Review(), new Review(), new Review());
        loggedInUser = new User(); // Initialize with actual User object if available
    }

    @Test
    void testGetTopThreeFreeGames() {
        when(filterService.getTopThreeFreeGames()).thenReturn(topThreeFreeGames);

        List<Game> result = homeFacade.getTopThreeFreeGames();

        assertEquals(topThreeFreeGames, result);
        verify(filterService, times(1)).getTopThreeFreeGames();
    }

    @Test
    void testGetGamesTop6OrderByRating() {
        when(filterService.getGamesTop6OrderByRating()).thenReturn(topSixGamesByRating);

        List<Game> result = homeFacade.getGamesTop6OrderByRating();

        assertEquals(topSixGamesByRating, result);
        verify(filterService, times(1)).getGamesTop6OrderByRating();
    }

    @Test
    void testGetTop6Reviews() {
        when(reviewFilterService.findTop6ByRatingGreaterThanEqual(4)).thenReturn(topSixReviews);

        List<Review> result = homeFacade.getTop6Reviews();

        assertEquals(topSixReviews, result);
        verify(reviewFilterService, times(1)).findTop6ByRatingGreaterThanEqual(4);
    }

    @Test
    void testGetLoggedInUser() {
        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        User result = homeFacade.getLoggedInUser();

        assertEquals(loggedInUser, result);
        verify(userService, times(1)).getLoggedInUser();
    }
}
