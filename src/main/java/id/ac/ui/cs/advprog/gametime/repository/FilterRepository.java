package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilterRepository extends JpaRepository<Game, UUID> {

    List<Game> findGamesByGameDeletedAndTitleContainingIgnoreCaseAndStockGreaterThanOrderByTitle(boolean gameDeleted, String keyword, int stock);

    List<Game> findByGameDeletedAndStockGreaterThanOrderByTitle(boolean gameDeleted, int stock);

    List<Game> findTop3ByPriceAndGameDeletedEqualsAndStockGreaterThanOrderByAvgRatingDesc(int price, boolean isDeleted, int stock);

    List<Game> findTop6ByGameDeletedAndStockGreaterThanOrderByAvgRatingDesc(boolean gameDeleted, int stock);

    List<Game> findTop6ByGameDeletedAndPlatformAndStockGreaterThanOrderByAvgRatingDesc(boolean gameDeleted, String platform, int stock);
}