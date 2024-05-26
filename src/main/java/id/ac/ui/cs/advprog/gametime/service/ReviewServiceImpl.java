package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.event.ReviewEvent;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.observer.ReviewObserver;
import id.ac.ui.cs.advprog.gametime.repository.ReviewRepository;
import id.ac.ui.cs.advprog.gametime.repository.SellerResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SellerResponseRepository sellerResponseRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private final List<ReviewObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(ReviewObserver observer) {
        observers.add(observer);
    }
    @Override
    public void removeObserver(ReviewObserver observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(Review review) {
        for (ReviewObserver observer : observers) {
            observer.updateReview(review);
        }
    }

    List<ReviewObserver> getObservers() {
        return observers;
    }


    public void deleteReviewById(UUID id) {
        Review review = getReviewById(id);
        reviewRepository.deleteById(id);
        notifyObservers(review);
        eventPublisher.publishEvent(new ReviewEvent(this, review));
    }

    public Review getReviewById(UUID id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review addReview(Review review) {
        Review savedReview = reviewRepository.save(review);
        notifyObservers(savedReview);
        eventPublisher.publishEvent(new ReviewEvent(this, savedReview));
        return savedReview;
    }

    public Review updateReview(UUID id, Review review) {
        review.setReviewId(id);
        Review updatedReview = reviewRepository.save(review);
        notifyObservers(updatedReview);
        eventPublisher.publishEvent(new ReviewEvent(this, updatedReview));
        return updatedReview;
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

    public SellerResponse getSellerResponseByReviewId(UUID reviewId){
        return sellerResponseRepository.findByReview_ReviewId(reviewId);
    }

}
