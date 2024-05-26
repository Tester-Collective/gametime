package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GameReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private GameService gameService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private GameReviewController gameReviewController;

    private MockMvc mockMvc;
    private User user;
    private Game game;
    private Review review;
    private UUID gameId;
    private UUID reviewId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameReviewController).build();

        gameId = UUID.randomUUID();
        reviewId = UUID.randomUUID();

        user = new User();
        user.setUserID(UUID.randomUUID());

        game = new Game();
        game.setId(gameId);

        review = new Review();
        review.setReviewId(reviewId);
        review.setGame(game);
        review.setUser(user);
        review.setRating(4.5f);
        review.setReviewDate(LocalDateTime.now());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGameDetails() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(userService.getLoggedInUser()).thenReturn(user);
        when(reviewService.findReviewsByGameIdAndUserId(gameId, user.getUserID())).thenReturn(reviews);
        when(reviewService.findReviewsByGameId(gameId)).thenReturn(reviews);
        when(gameService.getGameById(gameId.toString())).thenReturn(game);
        when(reviewService.getReviewCountByGame(gameId)).thenReturn(1);
        when(reviewService.calculateGameRatingAverage(gameId)).thenReturn(4.5f);

        String viewName = gameReviewController.gameDetails(model, gameId.toString());

        assertEquals("game/buyer/details", viewName);
        verify(model).addAttribute("reviewService", reviewService);
        verify(model).addAttribute("userReviews", reviews);
        verify(model).addAttribute("reviews", reviews);
        verify(model).addAttribute("game", game);
        verify(model).addAttribute("reviewCountByGame", 1);
        verify(model).addAttribute("avgRatingByGame", 4.5f);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testAddReviewPost() {
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(gameService.getGameById(gameId.toString())).thenReturn(game);

        Review reviewInput = new Review();
        reviewInput.setRating(5);
        reviewInput.setReviewTitle("Great Game");
        reviewInput.setReviewText("Had a lot of fun playing this game!");

        String viewName = gameReviewController.addReviewPost(reviewInput, gameId.toString());

        assertEquals("redirect:/game/buyer/details/" + gameId, viewName);
        verify(reviewService, times(1)).addReview(any(Review.class));
    }

    @Test
    void testAddReviewPage() {
        when(gameService.getGameById(gameId.toString())).thenReturn(game);
        when(userService.getLoggedInUser()).thenReturn(user);

        String viewName = gameReviewController.addReviewPage(model, gameId.toString());

        assertEquals("game/buyer/review/addReview", viewName);
        verify(model).addAttribute(eq("review"), any(Review.class));
        verify(model).addAttribute("user", user);
    }

    @Test
    void testDeleteReviewPost() {
        String viewName = gameReviewController.deleteReviewPost(reviewId.toString(), gameId.toString());

        assertEquals("redirect:/game/buyer/details/" + gameId, viewName);
        verify(reviewService, times(1)).deleteReviewById(reviewId);
    }

    @Test
    void testEditReviewPost() {
        when(reviewService.getReviewById(reviewId)).thenReturn(review);

        Review reviewInput = new Review();
        reviewInput.setReviewTitle("Updated Title");
        reviewInput.setRating(4);
        reviewInput.setReviewText("Updated review text.");

        String viewName = gameReviewController.editReviewPost(reviewInput, reviewId.toString(), gameId.toString());

        assertEquals("redirect:/game/buyer/details/" + gameId, viewName);
        verify(reviewService, times(1)).updateReview(eq(reviewId), any(Review.class));
    }

    @Test
    void testEditReviewPage() {
        when(reviewService.getReviewById(reviewId)).thenReturn(review);
        when(userService.getLoggedInUser()).thenReturn(user);

        String viewName = gameReviewController.editReviewPage(model, reviewId.toString(), gameId.toString());

        assertEquals("game/buyer/review/editReview", viewName);
        verify(model).addAttribute("editReview", review);
        verify(model).addAttribute("reviewId", reviewId.toString());
        verify(model).addAttribute("user", user);
    }
}
