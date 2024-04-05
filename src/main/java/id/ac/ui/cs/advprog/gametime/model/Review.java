package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter
public class Review {
    private UUID reviewId;
    private String reviewTitle;
    private float rating;
    private Set<String> sellerResponses;
    private String reviewText;

    private UUID userID;
    private UUID gameID;


    public Review(UUID reviewId, String title, float rating, String reviewText){
        this.reviewId = reviewId;
        this.reviewTitle = title;
        this.reviewText = reviewText;
        this.sellerResponses = new HashSet<>();

        if (rating <= 5.0 || rating >= 0.0){
            this.rating = rating;
        } else{
            throw new IllegalArgumentException();
        }
    }
    public void addSellerResponse(String response){
        this.sellerResponses.add(response);
    }

    public void removeSellerResponse(String response){
        this.sellerResponses.remove(response);
    }


}
