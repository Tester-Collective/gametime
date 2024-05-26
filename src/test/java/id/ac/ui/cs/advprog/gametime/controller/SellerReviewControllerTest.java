package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.SellerResponse;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

class SellerReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SellerReviewController sellerReviewController;

    private MockMvc mockMvc;
    private UUID reviewId;
    private UUID gameId;
    private UUID responseId;
    private Review review;
    private String sellerResponseText;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerReviewController).build();

        reviewId = UUID.randomUUID();
        gameId = UUID.randomUUID();
        responseId = UUID.randomUUID();

        review = new Review();
        review.setReviewId(reviewId);

        sellerResponseText = "Thank you for your feedback!";
    }

    @Test
    void testAddResponse() throws Exception {
        when(reviewService.getReviewById(reviewId)).thenReturn(review);

        mockMvc.perform(post("/game/seller/{gameId}/add-response/{reviewId}", gameId, reviewId)
                        .param("seller-response", sellerResponseText))
                .andExpect(redirectedUrl("/game/seller/" + gameId));

        verify(reviewService, times(1)).getReviewById(reviewId);
        verify(reviewService, times(1)).addSellerResponse(review, sellerResponseText);
    }

    @Test
    void testDeleteResponse() throws Exception {
        mockMvc.perform(post("/game/seller/{gameId}/delete-response/{reviewId}/{responseId}", gameId, reviewId, responseId))
                .andExpect(redirectedUrl("/game/seller/" + gameId));

        verify(reviewService, times(1)).deleteSellerResponse(responseId);
    }
}
