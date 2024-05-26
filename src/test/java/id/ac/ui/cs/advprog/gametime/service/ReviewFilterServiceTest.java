package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.repository.ReviewFilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewFilterServiceTest {

    @Mock
    private ReviewFilterRepository reviewFilterRepository;

    @InjectMocks
    private ReviewFilterServiceImpl reviewFilterService;

    private List<Review> topSixReviews;

    @BeforeEach
    void setUp() {
        topSixReviews = Arrays.asList(
                new Review(UUID.randomUUID(), "Review 1", 5.0f, "Excellent game!"),
                new Review(UUID.randomUUID(), "Review 2", 4.5f, "Great game!"),
                new Review(UUID.randomUUID(), "Review 3", 4.2f, "Very good game!"),
                new Review(UUID.randomUUID(), "Review 4", 4.1f, "Good game!"),
                new Review(UUID.randomUUID(), "Review 5", 4.0f, "Nice game!"),
                new Review(UUID.randomUUID(), "Review 6", 4.0f, "Decent game!")
        );
    }

    @Test
    void testFindTop6ByRatingGreaterThanEqual() {
        float rating = 4.0f;
        when(reviewFilterRepository.findTop6ByRatingGreaterThanEqualOrderByRatingDesc(rating)).thenReturn(topSixReviews);

        List<Review> result = reviewFilterService.findTop6ByRatingGreaterThanEqual(rating);

        assertEquals(topSixReviews, result);
    }
}