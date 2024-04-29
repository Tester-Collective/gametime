package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepository {
    private List<Review> reviewList = new ArrayList<>();

    public Review create(Review review){
        if (review.getReviewId() == null){
            UUID uuid = UUID.randomUUID();
            review.setReviewId(uuid);
        }

        reviewList.add(review);
        return review;
    }

    public Review findById(UUID id){
        for (Review review : reviewList){
            if (review.getReviewId().equals(id)){
                return review;
            }
        }
        return null;
    }

    public void delete(UUID id){reviewList.removeIf(review -> review.getReviewId().equals(id));}

    public Review update(UUID id, Review updatedReview){
        for(int i = 0; i < reviewList.size(); i++){
            Review review = reviewList.get(i);
            if(review.getReviewId().equals(id)){
                review.setReviewTitle(updatedReview.getReviewTitle());
                review.setSellerResponses(updatedReview.getSellerResponses());
                review.setReviewText(updatedReview.getReviewText());
                review.setRating(updatedReview.getRating());
                return review;
            }
        }

        return null;
    }

    public List<Review> findAll(){
        return reviewList;
    }

    public Review addSellerResponse(UUID Id, String response){
        for (Review review : reviewList){
            if (review.getReviewId().equals(Id)){
                String responseId = UUID.randomUUID().toString();
                review.addSellerResponse(responseId, response);
                return review;
            }
        }
        return null;
    }

    public Review deleteSellerResponse(UUID id, String responseId){
        for (Review review : reviewList){
            if (review.getReviewId().equals(id)){
                if (review.getSellerResponses().containsKey(responseId)){
                    review.removeSellerResponse(responseId);
                    return review;
                }
                return null;
            }
        }
        return null;
    }
}
