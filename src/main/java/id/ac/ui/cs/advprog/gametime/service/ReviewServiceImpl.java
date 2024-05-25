package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.ReviewRepository;
import id.ac.ui.cs.advprog.gametime.repository.SellerResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SellerResponseRepository sellerResponseRepository;

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
        return reviewRepository.findReviewsByGameId(gameId);
    }
    public List<Review> findReviewsByUser(UUID userId) {
        return reviewRepository.findReviewsByUser_UserID(userId);
    }

    public List<Review> findReviewsByGameIdAndUserId(UUID gameId, UUID userId) {
        List<Review> reviews = reviewRepository.findReviewsByGameId(gameId);
        List<Review> userReviews = reviewRepository.findReviewsByUser_UserID(userId);
        reviews.retainAll(userReviews);
        return reviews;
    }

    public List<Review> findReviewsByGameSeller(User seller) {

        return reviewRepository.findReviewsByGameSeller(seller);
    }

    public float calculateGameRatingAverage(UUID gameId) {
        List<Review> reviews = reviewRepository.findReviewsByGameId(gameId);
        float totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        return Math.round((totalRating / reviews.size()) * 10) / 10.0f;
    }

    public Integer getReviewCountByGame(UUID gameId) {
        return reviewRepository.countByGame_Id(gameId);
    }

    public void addSellerResponse(Review review, String response){
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setReview(review);
        sellerResponse.setResponse(response);
        sellerResponseRepository.save(sellerResponse);
    }

    public void deleteSellerResponse(UUID responseId){
        sellerResponseRepository.deleteById(responseId);
    }

    public SellerResponse getSellerResponse(UUID reviewId){
        return sellerResponseRepository.findByReview_ReviewId(reviewId);
    }

}
