package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/game/seller/transaction")
public class SellerTransactionController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @GetMapping("")
    public String transactionHistory(Model model) {
        User seller = userService.getLoggedInUser();
        List<Transaction> transactions = transactionService.findAllTransactionofSeller(seller);
        if (transactions.isEmpty()) {
            model.addAttribute("user", seller);
            return "game/seller/transaction/empty";
        }
        model.addAttribute("user", seller);
        return "game/seller/transaction/sellerTransactionHistory";
    }
}