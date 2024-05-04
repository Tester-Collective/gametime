package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
    List<Transaction> findByUserId(UUID userId);
    List<Transaction> findByOrder(Order order);
}