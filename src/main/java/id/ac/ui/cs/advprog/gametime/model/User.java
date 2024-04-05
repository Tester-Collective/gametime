package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class User {
    private UUID userID;
    private String email;
    private String password;
    private String username;
    private String profilePicture;
    private String bio;
    private int balance;
    private boolean isSeller;
    private boolean isAdmin;
}
