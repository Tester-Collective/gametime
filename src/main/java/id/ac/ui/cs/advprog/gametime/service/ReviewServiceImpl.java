package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void deleteReviewById(UUID id) {
        reviewRepository.deleteById(id);
    }

    public Review getReviewById(UUID id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(UUID id, Review review) {
        review.setReviewId(id);
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> findReviewsByGameId(UUID gameId) {
        return reviewRepository.findReviewByGameID(gameId);
    }
    public List<Review> findReviewsByUser(UUID userId) {
        return reviewRepository.findReviewByUserID(userId);
    }

    public List<Review> findReviewsByGameIdAndUserId(UUID gameId, UUID userId) {
        List<Review> reviews = reviewRepository.findReviewByGameID(gameId);
        List<Review> userReviews = reviewRepository.findReviewByUserID(userId);
        reviews.retainAll(userReviews);
        return reviews;
    }

}
