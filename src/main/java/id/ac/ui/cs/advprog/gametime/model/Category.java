package id.ac.ui.cs.advprog.gametime.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Game> games = new HashSet<>();
}
