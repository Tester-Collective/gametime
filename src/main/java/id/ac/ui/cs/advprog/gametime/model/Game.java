package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class Game {
    private UUID sellerId;
    private UUID id;
    private String title;
    private Set<String> category = new HashSet<>();
    private String description;
    private int price;
    private int stock;
    private String imageLink;
    private Set<UUID> reviewIdSet = new HashSet<>();

    public void addCategory(String category) {
        this.category.add(category);
    }
    public void removeCategory(String category) {
        this.category.remove(category);
    }

    public void addReview(UUID reviewId) {
        this.reviewIdSet.add(reviewId);
    }

    public void removeReview(UUID reviewId) {
        this.reviewIdSet.remove(reviewId);
    }
}
