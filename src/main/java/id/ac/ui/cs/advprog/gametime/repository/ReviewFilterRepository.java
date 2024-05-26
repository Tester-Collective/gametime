package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewFilterRepository extends JpaRepository<Review, UUID> {

    List<Review> findTop6ByRatingGreaterThanEqualOrderByRatingDesc(float rating);
}