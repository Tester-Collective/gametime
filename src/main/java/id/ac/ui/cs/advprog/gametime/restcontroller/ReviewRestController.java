package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/delete-response/{responseId}")
    public ResponseEntity<?> deleteSellerResponse(@PathVariable UUID responseId){
        reviewService.deleteSellerResponse(responseId);
        return ResponseEntity.ok().build();
    }


}
