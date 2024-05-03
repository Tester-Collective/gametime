package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "review")
@Getter @Setter
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID reviewId;

    @Column(nullable = false)
    private String reviewTitle;

    @Column(nullable = false)
    private float rating;

    @ElementCollection
    @CollectionTable(name = "seller_response", joinColumns = @JoinColumn(name = "review_id"))
    @MapKeyColumn(name = "response_id")
    @Column(name="seller_response")
    private Map<String, String> sellerResponses;

    @Column(nullable = false)
    private String reviewText;

    @ManyToOne
    @JoinTable(
            name = "user_review",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private User user;

    @ManyToOne
    @JoinTable(
            name = "game_review",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private Game game;


    public Review(){
        this.reviewId = UUID.randomUUID();
        this.sellerResponses = new HashMap<>() ;
    }
    public Review(UUID reviewId, String title, float rating, String reviewText){
        this.reviewId = reviewId;
        this.reviewTitle = title;
        this.reviewText = reviewText;
        this.sellerResponses = new HashMap<>();

        if (rating <= 5.0 && rating >= 0.0){
            this.rating = rating;
        } else{
            throw new IllegalArgumentException();
        }
    }
    public void addSellerResponse(String responseId, String response){
        this.sellerResponses.put(responseId, response);
    }

    public void removeSellerResponse(String responseId){
        this.sellerResponses.remove(responseId);
    }


}
