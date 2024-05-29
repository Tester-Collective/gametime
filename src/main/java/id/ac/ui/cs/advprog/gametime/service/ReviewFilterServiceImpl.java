package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.repository.ReviewFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewFilterServiceImpl implements ReviewFilterService {
    @Autowired
    private ReviewFilterRepository reviewFilterRepository;

    public List<Review> findTop6ByRatingGreaterThanEqual(float rating) {
        return reviewFilterRepository.findTop6ByRatingGreaterThanEqualOrderByRatingDesc(rating);
    }
}
