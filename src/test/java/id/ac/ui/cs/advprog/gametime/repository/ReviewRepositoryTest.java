package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    public void setUp() {
        review = new Review(UUID.randomUUID(), "Title", 4.5f, "Text");
    }

    @Test
    public void testCreate() {
        // Positive case: Creating a review
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review createdReview = reviewRepository.save(review);
        assertNotNull(createdReview.getReviewId());
        assertTrue(reviewRepository.findAll().contains(createdReview));

        // Negative case: Attempting to create a review with a non-null reviewId
        assertThrows(IllegalArgumentException.class, () -> reviewRepository.save(new Review(UUID.randomUUID(), "Title", 4.5f, "Text")));
    }

    @Test
    public void testFindById() {
        // Positive case: Finding an existing review
        UUID id = review.getReviewId();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        Optional<Review> foundReview = reviewRepository.findById(id);
        assertTrue(foundReview.isPresent());
        assertEquals(review, foundReview.get());

        // Negative case: Finding a non-existing review
        when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertFalse(reviewRepository.findById(UUID.randomUUID()).isPresent());
    }

    @Test
    public void testDelete() {
        // Positive case: Deleting an existing review
        UUID id = review.getReviewId();
        doNothing().when(reviewRepository).deleteById(id);

        reviewRepository.save(review);
        reviewRepository.deleteById(id);
        assertNull(reviewRepository.findById(id).orElse(null));

        // Negative case: Deleting a non-existing review
        assertDoesNotThrow(() -> reviewRepository.deleteById(UUID.randomUUID()));
    }

    @Test
    public void testUpdate() {
        // Positive case: Updating an existing review
        UUID id = review.getReviewId();
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        reviewRepository.save(review);
        Review updatedReview = new Review(id, "Updated Title", 3.5f, "Updated Text");
        reviewRepository.save(updatedReview);

        Optional<Review> foundReview = reviewRepository.findById(id);
        assertTrue(foundReview.isPresent());
        assertEquals("Updated Title", foundReview.get().getReviewTitle());
        assertEquals(3.5f, foundReview.get().getRating());
        assertEquals("Updated Text", foundReview.get().getReviewText());

        // Negative case: Attempting to update a non-existing review
        when(reviewRepository.save(any(Review.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> reviewRepository.save(new Review(UUID.randomUUID(), "Title", 4.5f, "Text")));
    }

    @Test
    public void testAddSellerResponse() {
        // Positive case: Adding a seller response to an existing review
        UUID id = review.getReviewId();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        reviewRepository.save(review);

        review.addSellerResponse(UUID.randomUUID().toString(), "Seller response");
        Optional<Review> updatedReview = reviewRepository.findById(id);
        assertTrue(updatedReview.isPresent());
        assertTrue(updatedReview.get().getSellerResponses().containsValue("Seller response"));

        // Negative case: Adding a seller response to a non-existing review
        when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertFalse(reviewRepository.findById(UUID.randomUUID()).isPresent());
    }

    @Test
    public void testDeleteSellerResponse() {
        // Positive case: Deleting a seller response from an existing review
        UUID id = review.getReviewId();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        reviewRepository.save(review);
        review.addSellerResponse(UUID.randomUUID().toString(), "Seller response");
        String responseId = review.getSellerResponses().keySet().iterator().next();
        review.removeSellerResponse(responseId);
        Optional<Review> updatedReview = reviewRepository.findById(id);
        assertTrue(updatedReview.isPresent());
        assertFalse(updatedReview.get().getSellerResponses().containsKey(responseId));

        // Negative case: Deleting a seller response from a non-existing review
        when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertFalse(reviewRepository.findById(UUID.randomUUID()).isPresent());
    }
}
