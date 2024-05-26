package id.ac.ui.cs.advprog.gametime.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.CartRepository;
import id.ac.ui.cs.advprog.gametime.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setAdmin(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("test");

        verify(userRepository).findByUsername("test");

        assertEquals("test", userDetails.getUsername());
        assertEquals("test", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_SELLER")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testLoadUserByUsernameSeller() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("test");

        verify(userRepository).findByUsername("test");

        assertEquals("test", userDetails.getUsername());
        assertEquals("test", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_SELLER")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_USER")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    public void testLoadUserByUsernameNoRole() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("test");

        verify(userRepository).findByUsername("test");

        assertEquals("test", userDetails.getUsername());
        assertEquals("test", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_USER")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")));
        assertFalse(userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_SELLER")));
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

        assertEquals("test", userFound.getUsername());
        assertEquals("test", userFound.getPassword());
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
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userRegistered = userService.registerUser("test", "test@test.com", "test", "test", true);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("test", userRegistered.getUsername());
        assertEquals("test", userRegistered.getPassword());
        assertEquals(0, userRegistered.getBalance());
        assertEquals(true, userRegistered.isSeller());
    }

    @Test
    public void testRegisterUserEmptyUsername() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("", "test@test.com", "test", "test", true));
    }

    @Test
    public void testRegisterUserEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser("test", "", "test", "test", true));
    }

    @Test
    public void testRegisterUserEmptyPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("test", "test@test.com", "", "test", true));
    }

    @Test
    public void testRegisterUserEmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("test", "test@test.com", "test", "", true));
    }

    @Test
    public void testRegisterUserSellerTrue() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userRegistered = userService.registerUser("test", "test@test.com", "test", "test", true);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("test", userRegistered.getUsername());
        assertEquals("test", userRegistered.getPassword());
        assertEquals(0, userRegistered.getBalance());
        assertEquals(true, userRegistered.isSeller());
    }

    @Test
    public void testRegisterUserAlreadyExistsUsername() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("test", "test@test.com", "test", "test", true));
    }

    @Test
    public void testRegisterUserAlreadyExistsEmail() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("test", "test@test.com", "test", "test", true));
    }

    @Test
    public void testRegisterUserWithUsernameEdit() {
        User user = new User();

        user.setUsername("edit");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("edit")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("edit", "test@test.com", "test", "test", true));
    }

    @Test
    public void testRegisterUserPasswordNotMatch() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser("test", "test@test.com", "test", "test2", true));
    }

    @Test
    public void testChangePassword() {
        User user = new User();

        String newPassword = new BCryptPasswordEncoder().encode("test2");
        user.setUsername("test");
        user.setPassword(newPassword);
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User newUser = userService.changePassword(user, "test2", "test2");

        verify(userRepository).save(any(User.class));

        assertTrue(new BCryptPasswordEncoder().matches("test2", newUser.getPassword()));
    }

    @Test
    public void testChangePasswordNotMatch() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThrows(IllegalArgumentException.class,
                () -> userService.changePassword(user, "test2", "test"));
    }

    @Test
    public void testEditUser() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User newUser = userService.editUser(user);

        verify(userRepository).save(user);

        assertEquals("test", newUser.getUsername());
        assertEquals("test", newUser.getPassword());
        assertEquals(true, newUser.isSeller());
    }

    @Test
    public void testGetLoggedInUser() {
        User user = new User();
        SecurityContextHolder.setContext(securityContext);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("test", "test");

        user.setUsername("test");
        user.setPassword("test");
        user.setSeller(true);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(securityContext.getAuthentication()).thenReturn(authentication);

        User loggedInUser = userService.getLoggedInUser();

        verify(userRepository).findByUsername("test");

        assertEquals("test", loggedInUser.getUsername());
        assertEquals("test", loggedInUser.getPassword());
    }
}
