package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    private Review review;
    private UUID reviewId;

    @Mock
    private User user;

    @Mock
    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewId = UUID.randomUUID();
        review = new Review(reviewId, "Great Game", 4.5f, "This is a detailed review text.");
        review.setUser(user);
        review.setGame(game);
        review.setReviewDate(LocalDateTime.now());
    }

    @Test
    void testDefaultConstructor() {
        Review defaultReview = new Review();
        assertNotNull(defaultReview.getReviewId());
        assertNull(defaultReview.getReviewTitle());
        assertNull(defaultReview.getReviewText());
    }

    @Test
    void testParameterizedConstructorValidRating() {
        assertEquals(reviewId, review.getReviewId());
        assertEquals("Great Game", review.getReviewTitle());
        assertEquals(4.5f, review.getRating());
        assertEquals("This is a detailed review text.", review.getReviewText());
    }

    @Test
    void testParameterizedConstructorInvalidRatingTooHigh() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Review(reviewId, "Bad Game", 5.5f, "This is a bad review text.");
        });
        assertEquals(IllegalArgumentException.class, thrown.getClass());
    }

    @Test
    void testParameterizedConstructorInvalidRatingTooLow() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Review(reviewId, "Bad Game", -0.5f, "This is a bad review text.");
        });
        assertEquals(IllegalArgumentException.class, thrown.getClass());
    }

    @Test
    void testSetAndGetUser() {
        review.setUser(user);
        assertEquals(user, review.getUser());
    }

    @Test
    void testSetAndGetGame() {
        review.setGame(game);
        assertEquals(game, review.getGame());
    }

    @Test
    void testSetAndGetReviewDate() {
        LocalDateTime now = LocalDateTime.now();
        review.setReviewDate(now);
        assertEquals(now, review.getReviewDate());
    }

    @Test
    void testGetFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        review.setReviewDate(now);
        String expectedDate = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy HH:mm"));
        assertEquals(expectedDate, review.getFormattedDate());
    }

    @Test
    void testSetAndGetReviewTitle() {
        review.setReviewTitle("Updated Title");
        assertEquals("Updated Title", review.getReviewTitle());
    }

    @Test
    void testSetAndGetReviewText() {
        review.setReviewText("Updated review text.");
        assertEquals("Updated review text.", review.getReviewText());
    }

    @Test
    void testSetAndGetRating() {
        review.setRating(3.5f);
        assertEquals(3.5f, review.getRating());
    }
}
