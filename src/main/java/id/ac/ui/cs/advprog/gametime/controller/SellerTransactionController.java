package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/game/seller/transaction")
public class SellerTransactionController {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String transactionHistory(Model model) {
        User seller = userService.getLoggedInUser();
        model.addAttribute("user", seller);
        return "game/seller/transaction/sellerTransactionHistory";
    }
}