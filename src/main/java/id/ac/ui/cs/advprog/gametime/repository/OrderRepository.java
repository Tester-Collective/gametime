package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order findOrderByCart_Customer_UserID(UUID userId);
    List<Order> findOrdersByCart_Customer_UserID(UUID userId);
    Order findTopByCart_Customer_UserIDOrderByOrderDateDesc(UUID userId);
}
