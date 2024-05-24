package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;

import java.util.List;
import java.util.UUID;

public interface ReviewService{
    void deleteReviewById(UUID id);
    Review getReviewById(UUID id);
    Review addReview(Review review);
    Review updateReview(UUID id, Review review);
    List<Review> getAllReviews();
    public List<Review> findReviewsByGameId(UUID gameId);
    List<Review> findReviewsByUser(UUID userId);
    List<Review> findReviewsByGameIdAndUserId(UUID gameId, UUID userId);
    List<Review> findReviewsByGameSeller(User seller);
    float calculateGameRatingAverage(UUID gameId);
    Integer getReviewCountByGame(UUID gameId);

}
