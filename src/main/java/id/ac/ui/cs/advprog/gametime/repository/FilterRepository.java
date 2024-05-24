package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilterRepository extends JpaRepository<Game, UUID> {

    @Query("SELECT g FROM Game g WHERE LOWER(g.title) LIKE LOWER(concat('%', ?1, '%'))")
    List<Game> findByTitleIgnoreCaseOrderByTitle(String keyword);

    List<Game> findByCategoryOrderByTitle(Category category);

    List<Game> findByPlatformOrderByTitle(String platform);

    List<Game> findByPriceBetweenOrderByTitle(int minPrice, int maxPrice);

    List<Game> findByPriceEqualsOrderByTitle(int price);

    List<Game> findByOrderByTitle();
}