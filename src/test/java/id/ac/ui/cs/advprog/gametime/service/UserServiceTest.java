package id.ac.ui.cs.advprog.gametime.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        String[] roles = new String[] { "ADMIN" };

        user.setUsername("test");
        user.setPassword("test");
        user.setAdmin(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("test");

        verify(userRepository).findByUsername("test");

        assertEquals(userDetails.getUsername(), "test");
        assertEquals(userDetails.getPassword(), "test");
        assertEquals(userDetails.getAuthorities(), roles);
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test"));
    }

    @Test
    public void testFindByUsername() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setAdmin(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        User userFound = userService.findByUsername("test");

        verify(userRepository).findByUsername("test");

        assertEquals(userFound.getUsername(), "test");
        assertEquals(userFound.getPassword(), "test");
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        User userFound = userService.findByUsername("test");

        verify(userRepository).findByUsername("test");

        assertNull(userFound);
    }

    @Test
    public void testRegisterUser() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        User userRegistered = userService.registerUser("test", "test@test.com", "test", "test", true);

        verify(userRepository).save(user);

        assertEquals(userRegistered.getUsername(), "test");
        assertEquals(userRegistered.getPassword(), "test");
        assertEquals(userRegistered.getEmail(), "test@test.com");
        assertEquals(userRegistered.getBalance(), 0);
        assertEquals(userRegistered.isSeller(), true);
    }
}
