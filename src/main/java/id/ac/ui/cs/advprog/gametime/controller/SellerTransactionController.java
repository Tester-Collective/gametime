package id.ac.ui.cs.advprog.gametime.controller;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/game/seller/transaction")
public class SellerTransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String transactionHistory(Model model) {
        User seller = userService.getLoggedInUser();
        List<Transaction> transactions = transactionService.findAllTransactionofSeller(seller);
        List<Integer> revenues = new ArrayList<>();
        for (Transaction transaction : transactions) {
            int revenue = 0;
            for (Game game : transaction.getOrder().getGameQuantity().keySet()) {
                if (game.getSeller().equals(seller)) {
                    revenue += game.getPrice() * transaction.getOrder().getGameQuantity().get(game);
                }
            }
            revenues.add(revenue);
        }
        model.addAttribute("revenues", revenues);
        model.addAttribute("seller", seller);
        model.addAttribute("transactions", transactions);
        return "game/seller/transaction/sellerTransactionHistory";
    }
}
