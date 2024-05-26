package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.CartRepository;
import id.ac.ui.cs.advprog.gametime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .roles(getRoles(user.get()))
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User getLoggedInUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(null);
    }

    @Override
    public User registerUser(String username, String email, String password, String matchingPassword,
            boolean isSeller) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || matchingPassword.isEmpty()) {
            throw new IllegalArgumentException("Please fill all the form");
        } else if (isEmailExist(email)) {
            throw new IllegalArgumentException("Email already exist");
        } else if (username.equals("edit")) {
            throw new IllegalArgumentException("Username already exist");
        } else if (isUsernameExist(username)) {
            throw new IllegalArgumentException("Username already exist");
        } else if (!password.equals(matchingPassword)) {
            throw new IllegalArgumentException("Password doesn't match");
        } else {
            User user = new User.Builder()
                    .username(username)
                    .password(new BCryptPasswordEncoder().encode(password))
                    .email(email)
                    .isSeller(isSeller)
                    .build();
            User savedUser = userRepository.save(user);
            Cart cart = new Cart();
            cart.setCustomer(user);
            cartRepository.save(cart);

            return savedUser;
        }
    }

    @Override
    public User changePassword(User user, String password, String matchingPassword) {
        if (!password.equals(matchingPassword)) {
            throw new IllegalArgumentException("Passwords does not match!");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(password));
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(user);
    }

    private boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private String[] getRoles(User user) {
        if (user.isAdmin()) {
            return new String[] { "ADMIN" };
        } else if (user.isSeller()) {
            return new String[] { "SELLER" };
        } else {
            return new String[] { "USER" };
        }
    }
}
