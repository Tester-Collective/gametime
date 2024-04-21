package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
}
