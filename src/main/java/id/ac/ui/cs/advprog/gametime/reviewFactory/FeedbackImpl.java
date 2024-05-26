package id.ac.ui.cs.advprog.gametime.reviewFactory;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;

import java.util.UUID;

public class FeedbackImpl implements Feedback{
    @Override
    public Review createReview(UUID reviewId, String title, float rating, String reviewText) {
        return new Review(reviewId, title, rating, reviewText);
    }

    @Override
    public SellerResponse createSellerResponse(UUID responseId, Review review, String response) {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setResponseId(responseId);
        sellerResponse.setReview(review);
        sellerResponse.setResponse(response);
        return sellerResponse;
    }
}
