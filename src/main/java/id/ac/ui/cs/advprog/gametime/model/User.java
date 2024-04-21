package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.UUID;

@Getter @Setter @Entity @Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userID;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private String profilePicture;

    @Column
    private String bio;

    @Column(nullable = false)
    private int balance;

    @Column(nullable = false)
    private boolean isSeller;

    @Column(nullable = false)
    private boolean isAdmin;
}
