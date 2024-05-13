package id.ac.ui.cs.advprog.gametime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.ac.ui.cs.advprog.gametime.dto.UserDto;
import id.ac.ui.cs.advprog.gametime.model.Image;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.ImageService;
import id.ac.ui.cs.advprog.gametime.service.UserService;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/edit")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userService.findByUsername(authentication.getName());
        UserDto userDto = new UserDto();
        userDto.setCurrentProfilePicture(currentUser.getProfilePicture());
        userDto.setBio(currentUser.getBio());
        model.addAttribute("user", currentUser);
        model.addAttribute("userDto", userDto);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String postEditProfile(@ModelAttribute UserDto userDto, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userService.findByUsername(authentication.getName());
        if (!userDto.getBio().isEmpty()) {
            currentUser.setBio(userDto.getBio());
        }
        if (userDto.getProfilePicture().getSize() != 0) {
            Image uploadedImage = imageService.uploadImage(userDto.getProfilePicture());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("can_edit", username.equals(authentication.getName()));
        return "profile/view";
    }
}
