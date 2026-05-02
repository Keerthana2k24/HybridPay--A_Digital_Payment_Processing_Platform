package com.hybridpay.controller;

import com.hybridpay.entity.Transaction;
import com.hybridpay.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/txn")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{userId}/transfer")
    public Map<String, Object> transfer(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> body) {
        return transactionService.transfer(
            userId,
            (String) body.get("receiverEmail"),
            Double.parseDouble(body.get("amount").toString()),
            (String) body.getOrDefault("note", "")
        );
    }

  
    @PostMapping("/{userId}/upi")
    public Map<String, Object> upiTransfer(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> body) {
        return transactionService.upiTransfer(
            userId,
            (String) body.get("receiverUpi"),
            Double.parseDouble(body.get("amount").toString()),
            (String) body.getOrDefault("note", "")
        );
    }

    @PostMapping("/{userId}/deposit")
    public Map<String, Object> deposit(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> body) {
        return transactionService.deposit(
            userId,
            Double.parseDouble(body.get("amount").toString())
        );
    }

    @PostMapping("/{userId}/crypto")
    public Map<String, Object> crypto(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> body) {
        return transactionService.logCrypto(
            userId,
            (String) body.get("txHash"),
            Double.parseDouble(body.get("amount").toString()),
            (String) body.getOrDefault("note", "")
        );
    }

    @GetMapping("/{userId}/history")
    public List<Transaction> history(@PathVariable Long userId) {
        return transactionService.getHistory(userId);
    }
}
