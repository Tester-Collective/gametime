package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @GetMapping({ "/register/seller", "/register/buyer" })
    public String registerForm() {
        return "auth/register_form";
    }

    @GetMapping("/logout")
    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PostMapping("/register/seller")
    public String registerSeller(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String matchingPassword,
            RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(username, email, password, matchingPassword, true);
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/auth/register/seller";
        }
    }

    @PostMapping("/register/buyer")
    public String registerBuyer(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String matchingPassword,
            RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(username, email, password, matchingPassword, false);
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/auth/register/buyer";
        }
    }
}
