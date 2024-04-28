package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "game")
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "seller_id")
    private User seller;

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "game_category",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String imageLink;

    @Column(nullable = false)
    private String platform;

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }
}

