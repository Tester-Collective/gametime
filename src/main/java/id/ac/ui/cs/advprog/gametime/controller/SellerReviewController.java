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
@RequestMapping("/game/review/seller")
public class SellerReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    private static final String SELLER_REVIEW_PAGE = "redirect:/game/review/seller";

    @GetMapping("")
    public String index(Model model) {
        User seller = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        List<Review> seller_reviews  = reviewService.findReviewsByGameSeller(seller);
        model.addAttribute("reviews", seller_reviews);
        return SELLER_REVIEW_PAGE;
    }

    @GetMapping("/responses/{reviewId}")
    public String responses(Model model, @PathVariable UUID reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        List<String> seller_responses = new ArrayList<>();
        review.getSellerResponses().forEach((k, v) -> {seller_responses.add(v);});
        model.addAttribute("review", review);
        model.addAttribute("responses", seller_responses);
        return "game/review/seller/responses";
    }

    @PostMapping("/response/{reviewId}")
    public String addResponse(@PathVariable UUID reviewId, String response) {
        Review review = reviewService.getReviewById(reviewId);
        UUID responseId = UUID.randomUUID();
        review.addSellerResponse(responseId, response);
        return"game/review/seller/responses";
    }

    @PostMapping("/response/{reviewId}/{responseId}")
    public String deleteResponse(@PathVariable UUID reviewId, @PathVariable UUID responseId) {
        Review review = reviewService.getReviewById(reviewId);
        review.removeSellerResponse(responseId);
        return "game/review/seller/responses";
    }





}
