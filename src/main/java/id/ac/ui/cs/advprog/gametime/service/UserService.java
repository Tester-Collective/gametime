package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User getLoggedInUser();

    User registerUser(String username, String email, String password, String matchingPassword, boolean isSeller);

    User findByUsername(String username);

    User editUser(User user);

    User changePassword(User user, String password, String matchingPassword);
}
