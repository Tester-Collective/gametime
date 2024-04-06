package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class Game {
    private UUID id;
    private UUID sellerId;
    private String title;
    private Set<String> category = new HashSet<>();
    private String description;
    private int price;
    private int stock;
    private String imageLink;
    private String platform;

    public void addCategory(String category) {
        this.category.add(category);
    }
    public void removeCategory(String category) {
        this.category.remove(category);
    }
}
