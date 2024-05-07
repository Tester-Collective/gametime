package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/review/seller")
public class SellerReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    private static final String SELLER_REVIEW_PAGE = "redirect:/review/seller";

    @GetMapping("")
    public String index(Model model) {
        User seller = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        List<Review> seller_reviews  = reviewService.findReviewsByGameSeller(seller);
        model.addAttribute("reviews", seller_reviews);

        return "review/sellerResponses/index";
    }

    @GetMapping("/responses/{reviewId}")
    public String responses(Model model, @PathVariable UUID reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        Map<UUID, String> seller_responses = review.getSellerResponses();
        model.addAttribute("review", review);
        model.addAttribute("responses", seller_responses);
        return "review/sellerResponses/responses";
    }

    @PostMapping("/responses/{reviewId}")
    public String addResponse(@PathVariable UUID reviewId, String response) {
        Review review = reviewService.getReviewById(reviewId);
        UUID responseId = UUID.randomUUID();
        review.addSellerResponse(responseId, response);
        return"redirect:review/sellerResponses/responses";
    }

    @PostMapping("/responses/{reviewId}/{responseId}")
    public String deleteResponse(@PathVariable UUID reviewId, @PathVariable UUID responseId) {
        Review review = reviewService.getReviewById(reviewId);
        review.removeSellerResponse(responseId);
        return "redirect:review/sellerResponses/responses";
    }





}
