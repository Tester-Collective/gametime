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

    @Query("SELECT g FROM Game g WHERE g.title LIKE %?1%")
    List<Game> findByTitle(String keyword);

    List<Game> findByCategory(Category category);

    List<Game> findByPlatform(String platform);

    List<Game> findByPriceBetween(int minPrice, int maxPrice);
