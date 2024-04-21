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

}
