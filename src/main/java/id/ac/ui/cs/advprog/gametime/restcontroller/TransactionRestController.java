package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/buyer")
    public List<Transaction> getAllBuyerTransactions() {
        User user = userService.getLoggedInUser();
        return transactionService.findAllTransactionofUser(user);
    }

    @GetMapping("/seller")
    public List<Transaction> getAllSellerTransactions() {
        User user = userService.getLoggedInUser();
        return transactionService.findAllTransactionofSeller(user);
    }
}
