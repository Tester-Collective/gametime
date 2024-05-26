package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewFilterService {

    List<Review> findTop6ByRatingGreaterThanEqual(float rating);
}
