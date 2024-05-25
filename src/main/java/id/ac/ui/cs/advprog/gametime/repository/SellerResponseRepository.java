package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.SellerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellerResponseRepository extends JpaRepository<SellerResponse, UUID> {
    SellerResponse findByReview_ReviewId(UUID reviewId);
}
