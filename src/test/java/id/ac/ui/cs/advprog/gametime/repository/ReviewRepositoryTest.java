package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewRepositoryTest {

    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository = new ReviewRepository();
    }

    @Test
    void createReview_PositiveCase() {
        Review review = new Review();
        review.setReviewTitle("Test Review");
        review.setReviewText("This is a test review.");
        review.setRating(5);

        Review createdReview = reviewRepository.create(review);

        assertNotNull(createdReview.getReviewId());
        assertEquals("Test Review", createdReview.getReviewTitle());
        assertEquals("This is a test review.", createdReview.getReviewText());
        assertEquals(5, createdReview.getRating());
    }

    @Test
    void findById_PositiveCase() {
        Review review = new Review();
        UUID reviewId = UUID.randomUUID();
        review.setReviewId(reviewId);
        review.setReviewTitle("Test Review");
        review.setReviewText("This is a test review.");
        review.setRating(5);
        reviewRepository.create(review);

        Review foundReview = reviewRepository.findById(reviewId);

        assertNotNull(foundReview);
        assertEquals("Test Review", foundReview.getReviewTitle());
        assertEquals("This is a test review.", foundReview.getReviewText());
        assertEquals(5, foundReview.getRating());
    }

    @Test
    void findById_NegativeCase() {
        // Trying to find a review that doesn't exist
        Review foundReview = reviewRepository.findById(UUID.randomUUID());
        assertNull(foundReview);
    }

    @Test
    void delete_PositiveCase() {
        Review review = new Review();
        UUID reviewId = UUID.randomUUID();
        review.setReviewId(reviewId);
        reviewRepository.create(review);

        reviewRepository.delete(reviewId);

        assertNull(reviewRepository.findById(reviewId));
    }

    @Test
    void delete_NegativeCase() {
        // Trying to delete a review that doesn't exist
        reviewRepository.delete(UUID.randomUUID());
        // No exception should be thrown
    }

    @Test
    void update_PositiveCase() {
        Review review = new Review();
        UUID reviewId = UUID.randomUUID();
        review.setReviewId(reviewId);
        review.setReviewTitle("Old Title");
        review.setReviewText("Old Text");
        review.setRating(3);
        reviewRepository.create(review);

        Review updatedReview = new Review();
        updatedReview.setReviewTitle("New Title");
        updatedReview.setReviewText("New Text");
        updatedReview.setRating(4);

        Review result = reviewRepository.update(reviewId, updatedReview);

        assertNotNull(result);
        assertEquals("New Title", result.getReviewTitle());
        assertEquals("New Text", result.getReviewText());
        assertEquals(4, result.getRating());
    }

    @Test
    void update_NegativeCase() {
        // Trying to update a review that doesn't exist
        Review result = reviewRepository.update(UUID.randomUUID(), new Review());
        assertNull(result);
    }

    @Test
    void findAll_PositiveCase() {
        UUID id1 = UUID.randomUUID();
        Review review1 = new Review(id1,"Review 1", 5, "Text 1");
        reviewRepository.create(review1);

        UUID id2 = UUID.randomUUID();
        Review review2 = new Review(id2,"Review 2", 4, "Text 2");

        reviewRepository.create(review2);

        List<Review> allReviews = reviewRepository.findAll();

        assertEquals(2, allReviews.size());
    }

    @Test
    void addSellerResponse_PositiveCase() {
        Review review = new Review();
        UUID reviewId = UUID.randomUUID();
        review.setReviewId(reviewId);
        reviewRepository.create(review);

        // Add a seller response
        String response = "Thank you for your feedback!";
        Review updatedReview = reviewRepository.addSellerResponse(reviewId, response);

        assertNotNull(updatedReview);
        assertTrue(updatedReview.getSellerResponses().contains(response));
    }

    @Test
    void addSellerResponse_NegativeCase_ReviewNotFound() {
        // Trying to add a seller response to a review that doesn't exist
        Review updatedReview = reviewRepository.addSellerResponse(UUID.randomUUID(), "Test Response");
        assertNull(updatedReview);
    }

    @Test
    void deleteSellerResponse_PositiveCase() {
        Review review = new Review();
        UUID reviewId = UUID.randomUUID();
        review.setReviewId(reviewId);
        review.addSellerResponse("Thank you for your feedback!");
        reviewRepository.create(review);

        // Delete a seller response
        Review updatedReview = reviewRepository.deleteSellerResponse(reviewId, "Thank you for your feedback!");

        assertNotNull(updatedReview);
        assertFalse(updatedReview.getSellerResponses().contains("Thank you for your feedback!"));
    }

    @Test
    void deleteSellerResponse_NegativeCase_ReviewNotFound() {
        // Trying to delete a seller response from a review that doesn't exist
        Review updatedReview = reviewRepository.deleteSellerResponse(UUID.randomUUID(), "Test Response");
        assertNull(updatedReview);
    }

    @Test
    void deleteSellerResponse_NegativeCase_ResponseNotFound() {
        Review review = new Review();
        UUID reviewId = UUID.randomUUID();
        review.setReviewId(reviewId);
        reviewRepository.create(review);

        // Trying to delete a seller response that doesn't exist in the review
        Review updatedReview = reviewRepository.deleteSellerResponse(reviewId, "Non-existent Response");
        assertNull(updatedReview);
    }



}
