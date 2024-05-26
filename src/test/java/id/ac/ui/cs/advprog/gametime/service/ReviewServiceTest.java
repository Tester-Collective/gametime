package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.ReviewRepository;
import id.ac.ui.cs.advprog.gametime.repository.SellerResponseRepository;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class ReviewServiceTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SellerResponseRepository sellerResponseRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAddReview() {
        Review review = new Review();
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.addReview(review);

        assertEquals(review, result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void shouldGetReviewById() {
        Review review = new Review();
        UUID id = UUID.randomUUID();
        when(reviewRepository.findById(id)).thenReturn(java.util.Optional.of(review));

        Review result = reviewService.getReviewById(id);

        assertEquals(review, result);
        verify(reviewRepository, times(1)).findById(id);
    }

    @Test
    public void shouldDeleteReviewById() {
        Review review = new Review();
        UUID id = UUID.randomUUID();
        when(reviewRepository.findById(id)).thenReturn(java.util.Optional.of(review));

        reviewService.deleteReviewById(id);

        verify(reviewRepository, times(1)).deleteById(id);
    }

    @Test
    public void shouldUpdateReview() {
        Review review = new Review();
        UUID id = UUID.randomUUID();
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.updateReview(id, review);

        assertEquals(review, result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void shouldFindReviewsByGameId() {
        UUID gameId = UUID.randomUUID();
        reviewService.findReviewsByGameId(gameId);

        verify(reviewRepository, times(1)).findReviewsByGameId(gameId);
    }

    @Test
    public void shouldFindReviewsByUser() {
        UUID userId = UUID.randomUUID();
        reviewService.findReviewsByUser(userId);

        verify(reviewRepository, times(1)).findReviewsByUser_UserID(userId);
    }

    @Test
    public void shouldFindReviewsByGameSeller() {
        User seller = new User();
        reviewService.findReviewsByGameSeller(seller);

        verify(reviewRepository, times(1)).findReviewsByGameSeller(seller);
    }

    @Test
    public void shouldCalculateGameRatingAverage() {
        UUID gameId = UUID.randomUUID();
        reviewService.calculateGameRatingAverage(gameId);

        verify(reviewRepository, times(1)).findReviewsByGameId(gameId);
    }

    @Test
    public void shouldGetReviewCountByGame() {
        UUID gameId = UUID.randomUUID();
        reviewService.getReviewCountByGame(gameId);

        verify(reviewRepository, times(1)).countByGame_Id(gameId);
    }
}