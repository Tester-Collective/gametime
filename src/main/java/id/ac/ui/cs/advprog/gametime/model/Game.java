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
    @JoinColumn(nullable = true, name = "seller_id")
    private User seller;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String imageName;

    @Column
    private boolean gameDeleted = false;

    @Column(columnDefinition = "FLOAT DEFAULT 0.0")
    private float avgRating = 0.0f;
}

