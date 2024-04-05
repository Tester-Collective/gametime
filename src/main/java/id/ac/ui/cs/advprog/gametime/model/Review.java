package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter @Setter
public class Review {
    private UUID reviewId;
    private String reviewTitle;
    private float rating;
    private Set<String> sellerResponses;
    private String reviewText;

    private UUID userID;
    private UUID id;

    public void addSellerResponse(String response){
        this.sellerResponses.add(response);
    }

    public void removeSellerResponse(String response){
        this.sellerResponses.remove(response);
    }


}
