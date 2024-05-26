package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.ac.ui.cs.advprog.gametime.dto.UserDto;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.ImageService;
import id.ac.ui.cs.advprog.gametime.service.UserService;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/edit")
    public String editProfile(Model model) {
        User currentUser = userService.getLoggedInUser();
        UserDto userDto = new UserDto();
        userDto.setCurrentProfilePicture(currentUser.getProfilePicture());
        userDto.setBio(currentUser.getBio());
        model.addAttribute("user", currentUser);
        model.addAttribute("userDto", userDto);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String postEditProfile(@ModelAttribute UserDto userDto, Model model, RedirectAttributes redirectAttributes)
            throws IOException {
        User currentUser = userService.getLoggedInUser();
        if (!userDto.getBio().isEmpty()) {
            currentUser.setBio(userDto.getBio());
        }
        if (userDto.getProfilePicture().getSize() != 0) {
            File uploadedImage = imageService.uploadImageToFileSystem(userDto.getProfilePicture());
            if (currentUser.getProfilePicture() != null) {
                imageService.deleteImage(currentUser.getProfilePicture());
            }
            currentUser.setProfilePicture(uploadedImage.getName());
        }
        if (!userDto.getPassword().isEmpty() && !userDto.getConfirmPassword().isEmpty()) {
            try {
                userService.changePassword(currentUser, userDto.getPassword(), userDto.getConfirmPassword());
            } catch (IllegalArgumentException e) {
                redirectAttributes.addAttribute("error", e.getMessage());
                return "redirect:/profile/edit";
            }
        }
        userService.editUser(currentUser);
        return "redirect:/profile/" + currentUser.getUsername();
    }

    @GetMapping("/{username}")
    public String viewProfile(@PathVariable String username, Model model) {
        User currentUser = userService.findByUsername(username);
        User loggedInUser = userService.getLoggedInUser();
        model.addAttribute("viewUser", currentUser);
        model.addAttribute("can_edit", username.equals(loggedInUser.getUsername()));
        return "profile/view";
    }

    @GetMapping("")
    public String viewLoggedInProfile(Model model) {
        User user = userService.getLoggedInUser();
        if (user != null) {
            return "redirect:/profile/" + user.getUsername();
        } else {
            return "redirect:/auth/login";
        }
    }
}
