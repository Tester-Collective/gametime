package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Game> findGamesByGameDeletedAndStockGreaterThanOrderByTitle(boolean gameDeleted, int stock);
    List<Game> findGamesBySeller(User seller);
    List<Game> findGamesBySellerAndGameDeletedOrderByTitle(User seller, boolean gameDeleted);
    List<Game> findGamesByGameDeletedAndTitleContainingIgnoreCaseAndStockGreaterThanOrderByTitle(boolean gameDeleted, String keyword, int stock);
}
