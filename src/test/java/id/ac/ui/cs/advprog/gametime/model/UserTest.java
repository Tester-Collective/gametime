package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    User user;

    @Test
    void testGetUserID() {
        this.user = new User();
        this.user.setUserID(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.user.getUserID());
    }

    @Test
    void testGetEmail() {
        this.user = new User();
        this.user.setEmail("test@email.com");
        assertEquals("test@email.com", this.user.getEmail());
    }

    @Test
    void testGetPassword() {
        this.user = new User();
        this.user.setPassword("password");
        assertEquals("password", this.user.getPassword());
    }

    @Test
    void testGetUsername() {
        this.user = new User();
        this.user.setUsername("username");
        assertEquals("username", this.user.getUsername());
    }

    @Test
    void testGetProfilePicture() {
        this.user = new User();
        this.user.setProfilePicture("profile.jpg");
        assertEquals("profile.jpg", this.user.getProfilePicture());
    }

    @Test
    void testGetBio() {
        this.user = new User();
        this.user.setBio("bio");
        assertEquals("bio", this.user.getBio());
    }

    @Test
    void testGetBalance() {
        this.user = new User();
        this.user.setBalance(100);
        assertEquals(100, this.user.getBalance());
    }

    @Test
    void testIsSeller() {
        this.user = new User();
        this.user.setSeller(true);
        assertTrue(this.user.isSeller());
    }

    @Test
    void testIsAdmin() {
        this.user = new User();
        this.user.setAdmin(true);
        assertTrue(this.user.isAdmin());
    }

    @Test
    void testBuilder() {
        User user = new User.Builder()
                .email("test@email.com")
                .password("password")
                .username("username")
                .profilePicture("profile.jpg")
                .bio("bio")
                .balance(100)
                .isSeller(true)
                .isAdmin(false)
                .build();

        assertEquals("test@email.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("username", user.getUsername());
        assertEquals("profile.jpg", user.getProfilePicture());
        assertEquals("bio", user.getBio());
        assertEquals(100, user.getBalance());
        assertTrue(user.isSeller());
        assertFalse(user.isAdmin());
    }
}
