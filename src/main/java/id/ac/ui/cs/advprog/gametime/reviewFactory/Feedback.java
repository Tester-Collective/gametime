package id.ac.ui.cs.advprog.gametime.reviewFactory;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;

import java.util.UUID;

public interface Feedback {
    Review createReview(UUID reviewId, String title, float rating, String reviewText);
    SellerResponse createSellerResponse(UUID responseId, Review review, String response);
}
