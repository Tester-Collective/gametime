package id.ac.ui.cs.advprog.gametime.observer.implementation;

import id.ac.ui.cs.advprog.gametime.event.ReviewEvent;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.observer.ReviewObserver;
import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
import id.ac.ui.cs.advprog.gametime.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GameRatingUpdater implements ReviewObserver {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @EventListener
    public void handleReviewEvent(ReviewEvent reviewEvent) {
        Review review = reviewEvent.getReview();
        updateReview(review);
    }

    @Override
    public void updateReview(Review review) {
        Game game = review.getGame();
        double averageRating = calculateAverageRating(game.getId());
        game.setAvgRating((float) averageRating);
        gameRepository.save(game);
    }

    private double calculateAverageRating(UUID gameId) {
        List<Review> reviews = reviewRepository.findReviewsByGameId(gameId);
        if (reviews.isEmpty()) {
            return 0;
        }
        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        return Math.round((totalRating / reviews.size()) * 10) / 10.0;
    }

}
