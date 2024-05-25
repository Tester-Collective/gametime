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
    private Map<UUID, String> sellerResponses;

    @Column(nullable = false)
    private String reviewText;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private User user;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
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
    public void addSellerResponse(UUID responseId, String response){
        this.sellerResponses.put(responseId, response);
    }

    public void removeSellerResponse(UUID responseId){
        this.sellerResponses.remove(responseId);
    }



}
