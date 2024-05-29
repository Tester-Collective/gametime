package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.event.ReviewEvent;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.observer.ReviewObserver;
import id.ac.ui.cs.advprog.gametime.repository.ReviewRepository;
import id.ac.ui.cs.advprog.gametime.repository.SellerResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SellerResponseRepository sellerResponseRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Captor
    private ArgumentCaptor<ReviewEvent> eventCaptor;

    private Review review;
    private UUID reviewId;
    private UUID gameId;
    private UUID userId;
    private ReviewObserver observer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewId = UUID.randomUUID();
        gameId = UUID.randomUUID();
        userId = UUID.randomUUID();
        review = new Review();
        review.setReviewId(reviewId);
        review.setGame(new Game());
        review.setUser(new User());

        observer = mock(ReviewObserver.class);
    }

    @Test
    void testAddObserver() {
        reviewService.addObserver(observer);
        assertTrue(reviewService.getObservers().contains(observer));
    }

    @Test
    void testRemoveObserver() {
        reviewService.addObserver(observer);
        reviewService.removeObserver(observer);
        assertFalse(reviewService.getObservers().contains(observer));
    }

    @Test
    void testNotifyObservers() {
        reviewService.addObserver(observer);
        reviewService.notifyObservers(review);
        verify(observer, times(1)).updateReview(review);
    }

    @Test
    void testDeleteReviewById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteReviewById(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewId);
        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        assertEquals(review, eventCaptor.getValue().getReview());
    }

    @Test
    void testGetReviewById() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        Review foundReview = reviewService.getReviewById(reviewId);
        assertEquals(review, foundReview);
    }

    @Test
    void testUpdateReview() {
        when(reviewRepository.save(review)).thenReturn(review);

        Review updatedReview = reviewService.updateReview(reviewId, review);

        assertEquals(review, updatedReview);
        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        assertEquals(review, eventCaptor.getValue().getReview());
    }

    @Test
    void testGetAllReviews() {
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> allReviews = reviewService.getAllReviews();
        assertEquals(reviews, allReviews);
    }

    @Test
    void testFindReviewsByGameId() {
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findReviewsByGameId(gameId)).thenReturn(reviews);

        List<Review> gameReviews = reviewService.findReviewsByGameId(gameId);
        assertEquals(reviews, gameReviews);
    }

    @Test
    void testFindReviewsByUser() {
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findReviewsByUser_UserID(userId)).thenReturn(reviews);

        List<Review> userReviews = reviewService.findReviewsByUser(userId);
        assertEquals(reviews, userReviews);
    }

    @Test
    void testFindReviewsByGameIdAndUserId() {
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findReviewsByGameId(gameId)).thenReturn(reviews);
        when(reviewRepository.findReviewsByUser_UserID(userId)).thenReturn(reviews);

        List<Review> reviewsByGameAndUser = reviewService.findReviewsByGameIdAndUserId(gameId, userId);
        assertEquals(reviews, reviewsByGameAndUser);
    }

    @Test
    void testFindReviewsByGameSeller() {
        User seller = new User();
        seller.setUserID(userId);
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findReviewsByGameSeller(seller)).thenReturn(reviews);

        List<Review> sellerReviews = reviewService.findReviewsByGameSeller(seller);
        assertEquals(reviews, sellerReviews);
    }

    @Test
    void testCalculateGameRatingAverage() {
        review.setRating(4.5f);
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findReviewsByGameId(gameId)).thenReturn(reviews);

        float averageRating = reviewService.calculateGameRatingAverage(gameId);
        assertEquals(4.5f, averageRating);
    }

    @Test
    void testGetReviewCountByGame() {
        when(reviewRepository.countByGame_Id(gameId)).thenReturn(1);

        int count = reviewService.getReviewCountByGame(gameId);
        assertEquals(1, count);
    }

    @Test
    void testAddSellerResponse() {
        String responseText = "Thank you for your feedback!";
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setReview(review);
        sellerResponse.setResponse(responseText);

        reviewService.addSellerResponse(review, responseText);

        verify(sellerResponseRepository, times(1)).save(any(SellerResponse.class));
    }

    @Test
    void testDeleteSellerResponse() {
        UUID responseId = UUID.randomUUID();
        reviewService.deleteSellerResponse(responseId);

        verify(sellerResponseRepository, times(1)).deleteById(responseId);
    }

    @Test
    void testGetSellerResponseByReviewId() {
        SellerResponse sellerResponse = new SellerResponse();
        when(sellerResponseRepository.findByReview_ReviewId(reviewId)).thenReturn(sellerResponse);

        SellerResponse foundResponse = reviewService.getSellerResponseByReviewId(reviewId);
        assertEquals(sellerResponse, foundResponse);
    }
}
