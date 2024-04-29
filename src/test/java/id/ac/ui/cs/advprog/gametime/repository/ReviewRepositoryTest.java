package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewRepositoryTest {

    private ReviewRepository reviewRepository;
    private Review review;

    @BeforeEach
    public void setUp() {
        reviewRepository = new ReviewRepository();
        review = new Review(UUID.randomUUID(), "Title", 4.5f, "Text");
    }

    @Test
    public void testCreate() {
        // Positive case: Creating a review
        Review createdReview = reviewRepository.create(review);
        assertNotNull(createdReview.getReviewId());
        assertTrue(reviewRepository.findAll().contains(createdReview));

        // Negative case: Attempting to create a review with a non-null reviewId
        assertThrows(IllegalArgumentException.class, () -> reviewRepository.create(new Review(UUID.randomUUID(), "Title", 4.5f, "Text")));
    }

    @Test
    public void testFindById() {
        // Positive case: Finding an existing review
        reviewRepository.create(review);
        UUID id = review.getReviewId();
        Review foundReview = reviewRepository.findById(id);
        assertEquals(review, foundReview);

        // Negative case: Finding a non-existing review
        assertNull(reviewRepository.findById(UUID.randomUUID()));
    }

    @Test
    public void testDelete() {
        // Positive case: Deleting an existing review
        reviewRepository.create(review);
        UUID id = review.getReviewId();
        reviewRepository.delete(id);
        assertNull(reviewRepository.findById(id));

        // Negative case: Deleting a non-existing review
        assertDoesNotThrow(() -> reviewRepository.delete(UUID.randomUUID()));
    }

    @Test
    public void testUpdate() {
        // Positive case: Updating an existing review
        reviewRepository.create(review);
        UUID id = review.getReviewId();
        Review updatedReview = new Review(id, "Updated Title", 3.5f, "Updated Text");
        reviewRepository.update(id, updatedReview);
        Review foundReview = reviewRepository.findById(id);
        assertEquals("Updated Title", foundReview.getReviewTitle());
        assertEquals(3.5f, foundReview.getRating());
        assertEquals("Updated Text", foundReview.getReviewText());

        // Negative case: Attempting to update a non-existing review
        assertNull(reviewRepository.update(UUID.randomUUID(), updatedReview));
    }

    @Test
    public void testAddSellerResponse() {
        // Positive case: Adding a seller response to an existing review
        reviewRepository.create(review);
        UUID id = review.getReviewId();
        reviewRepository.addSellerResponse(id, "Seller response");
        Review updatedReview = reviewRepository.findById(id);
        assertTrue(updatedReview.getSellerResponses().containsValue("Seller response"));

        // Negative case: Adding a seller response to a non-existing review
        assertNull(reviewRepository.addSellerResponse(UUID.randomUUID(), "Seller response"));
    }

    @Test
    public void testDeleteSellerResponse() {
        // Positive case: Deleting a seller response from an existing review
        reviewRepository.create(review);
        UUID id = review.getReviewId();
        reviewRepository.addSellerResponse(id, "Seller response");
        String responseId = review.getSellerResponses().keySet().iterator().next();
        reviewRepository.deleteSellerResponse(id, responseId);
        Review updatedReview = reviewRepository.findById(id);
        assertFalse(updatedReview.getSellerResponses().containsKey(responseId));

        // Negative case: Deleting a seller response from a non-existing review
        assertNull(reviewRepository.deleteSellerResponse(UUID.randomUUID(), responseId));
    }
}
