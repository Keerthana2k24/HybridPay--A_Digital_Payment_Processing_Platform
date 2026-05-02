package com.hybridpay.controller;

import com.hybridpay.service.UserService;
import com.hybridpay.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;

    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}/dashboard")
    public Map<String, Object> dashboard(@PathVariable Long id) {
        return userService.getUser(id).map(user -> {
            Map<String, Object> stats = transactionService.getStats(id);
            return Map.of(
                "success", true,
                "name", user.getName(),
                "email", user.getEmail(),
                "balance", user.getBalance(),
                "upiId", user.getUpiId() != null ? user.getUpiId() : "",
                "walletAddress", user.getWalletAddress() != null ? user.getWalletAddress() : "",
                "totalTransactions", stats.get("totalTransactions"),
                "totalSent", stats.get("totalSent")
            );
        }).orElse(Map.of("success", false, "message", "User not found"));
    }

    @PostMapping("/{id}/link-upi")
    public Map<String, Object> linkUpi(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return userService.linkUpi(id, body.get("upiId"));
    }

    @PostMapping("/{id}/link-wallet")
    public Map<String, Object> linkWallet(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return userService.linkWallet(id, body.get("walletAddress"));
    }
}
