package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

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

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User registerUser(String username, String email, String password, String matchingPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || matchingPassword.isEmpty()) {
            throw new IllegalArgumentException("Please fill all the form");
        } else if (isEmailExist(email)) {
            throw new IllegalArgumentException("Email already exist");
        } else if (isUsernameExist(username)) {
            throw new IllegalArgumentException("Username already exist");
        } else {
            User user = new User();

            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setBalance(0);
            user.setSeller(false);
            user.setAdmin(false);

            return userRepository.save(user);
        }
    }

    private boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private String[] getRoles(User user) {
        if (user.isAdmin()) {
            return new String[]{"ADMIN"};
        } else if (user.isSeller()) {
            return new String[]{"SELLER"};
        } else {
            return new String[]{"USER"};
        }
    }
}
