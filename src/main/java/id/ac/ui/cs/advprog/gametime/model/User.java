package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userID;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
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

    public User() {
        this.email = "";
        this.password = "";
        this.username = "";
        this.profilePicture = "";
        this.bio = "";
        this.balance = 0;
        this.isSeller = false;
        this.isAdmin = false;
    }

    public User(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.username = builder.username;
        this.profilePicture = builder.profilePicture;
        this.bio = builder.bio;
        this.balance = builder.balance;
        this.isSeller = builder.isSeller;
        this.isAdmin = builder.isAdmin;
    }

    public static class Builder {
        private String email;
        private String password;
        private String username;
        private String profilePicture;
        private String bio;
        private int balance;
        private boolean isSeller;
        private boolean isAdmin;

        public Builder() {
            this.email = "";
            this.password = "";
            this.username = "";
            this.profilePicture = "";
            this.bio = "";
            this.balance = 0;
            this.isSeller = false;
            this.isAdmin = false;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder profilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder balance(int balance) {
            this.balance = balance;
            return this;
        }

        public Builder isSeller(boolean isSeller) {
            this.isSeller = isSeller;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
