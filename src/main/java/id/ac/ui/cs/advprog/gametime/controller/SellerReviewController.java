package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/game/seller")
public class SellerReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;


    @PostMapping("/{gameId}/add-response/{reviewId}")
    public String addResponse(@PathVariable UUID reviewId, @PathVariable UUID gameId, @RequestParam("seller-response")String sellerResponse) {
        Review review = reviewService.getReviewById(reviewId);
        reviewService.addSellerResponse(review, sellerResponse);
        return "redirect:/game/seller/" + gameId;
    }

    @PostMapping("{gameId}/delete-response/{reviewId}/{responseId}")
    public String deleteResponse(@PathVariable UUID reviewId, @PathVariable UUID responseId, @PathVariable UUID gameId){
        reviewService.deleteSellerResponse(responseId);
        return "redirect:/game/seller/" + gameId;
    }





}
