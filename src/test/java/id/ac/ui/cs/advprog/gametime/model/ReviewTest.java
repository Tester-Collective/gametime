package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class ReviewTest {
    private Review review;

    @BeforeEach
    public void setUp() {
        UUID reviewId = UUID.randomUUID();
        review = new Review(reviewId, "Test Review", 4.5f, "This is a test review");
    }

    @Test
    public void testAddSellerResponse() {
        review.addSellerResponse("Seller response 1");
        assertEquals(1, review.getSellerResponses().size());
    }

    @Test
    public void testRemoveSellerResponse() {
        review.addSellerResponse("Seller response 1");
        review.addSellerResponse("Seller response 2");
        review.removeSellerResponse("Seller response 1");
        assertEquals(1, review.getSellerResponses().size());
        assertFalse(review.getSellerResponses().contains("Seller response 1"));
    }

    @Test
    public void testConstructorWithValidRating() {
        assertEquals(4.5f, review.getRating());
    }

    @Test
    public void testConstructorWithInvalidRating() {
        assertThrows(IllegalArgumentException.class, () -> new Review(UUID.randomUUID(), "Test Review", 6.0f, "This is a test review"));
    }
}

