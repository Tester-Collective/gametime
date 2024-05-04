package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface GameInCartRepository extends JpaRepository<GameInCart, UUID> {
    List<GameInCart> findGameInCartsByCart_CartId(UUID cartId);

    void deleteAllByCart_CartId(UUID cartId);
}
