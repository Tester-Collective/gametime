package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(nullable = false, updatable = false)
    private LocalDateTime reviewDate;
    
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

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy HH:mm");
        return reviewDate.format(formatter);
    }
}
