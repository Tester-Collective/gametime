package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface GameInCartRepository extends JpaRepository<GameInCart, UUID> {
    void deleteAllByCart_CartId(UUID cartId);

    GameInCart findGameInCartByGame_IdAndCart_CartId(UUID gameId, UUID cartId);
}
