package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private Set<Category> categories = new HashSet<>();

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
        this.categories.add(category);
        category.getGames().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getGames().remove(this);
    }
}

