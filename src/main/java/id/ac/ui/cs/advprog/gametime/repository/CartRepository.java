package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findCartByOrderId (UUID cartId);

    List<Cart> finCartByUserId(UUID userId);

    Integer getTotalPriceByCartId(UUID cartId);


}
