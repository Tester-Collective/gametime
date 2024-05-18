package id.ac.ui.cs.advprog.gametime.service;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.TransactionRepository;
import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
import org. springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public Transaction get(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        return transaction;
    }
    @Override
    public Transaction updateStatus(UUID id, String status) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            transaction.setStatus(status);
            transactionRepository.delete(transaction);
            transactionRepository.save(transaction);
            return transaction;
        }
        return null;
    }
    @Override
    public List<Transaction> findAllTransactionofUser(User user) {
        List<Transaction> transactions = transactionRepository.findByUser(user);
        return transactions;
    }
    @Override
    public List<Transaction> findAllTransactionofSeller(User seller) {
        List<Game> games = gameRepository.findGamesBySeller(seller);
        List<Transaction> transactions = transactionRepository.findAll();
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            for (Game game : games) {
                if (transaction.getOrder().getGameQuantity().containsKey(game)) {
                    result.add(transaction);
                    break;
                }
            }
        }
        return result;
    }

}