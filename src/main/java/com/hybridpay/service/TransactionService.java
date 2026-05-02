package com.hybridpay.service;

import com.hybridpay.entity.Transaction;
import com.hybridpay.entity.User;
import com.hybridpay.repository.TransactionRepository;
import com.hybridpay.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Map<String, Object> transfer(Long senderId, String receiverEmail, Double amount, String note) {
        if (amount <= 0) return Map.<String, Object>of("success", false, "message", "Amount must be greater than 0");

        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findByEmail(receiverEmail);

        if (senderOpt.isEmpty()) return Map.<String, Object>of("success", false, "message", "Sender not found");
        if (receiverOpt.isEmpty()) return Map.<String, Object>of("success", false, "message", "Receiver email not found in HybridPay");

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        if (sender.getId().equals(receiver.getId()))
            return Map.<String, Object>of("success", false, "message", "Cannot transfer to yourself");

        if (sender.getBalance() < amount)
            return Map.<String, Object>of("success", false, "message", "Insufficient balance");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction txn = new Transaction();
        txn.setSenderId(senderId);
        txn.setReceiverId(receiverEmail);
        txn.setAmount(amount);
        txn.setType("TRANSFER");
        txn.setStatus("SUCCESS");
        txn.setMode("WALLET");
        txn.setNote(note != null ? note : "");
        transactionRepository.save(txn);

        return Map.<String, Object>of(
                "success", true,
                "message", "₹" + amount + " sent to " + receiver.getName(),
                "newBalance", sender.getBalance()
        );
    }

    @Transactional
    public Map<String, Object> upiTransfer(Long senderId, String receiverUpi, Double amount, String note) {
        if (amount <= 0) return Map.<String, Object>of("success", false, "message", "Amount must be greater than 0");

        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = receiverUpi.contains("@") ? userRepository.findByUpiId(receiverUpi) : Optional.empty();

        if (senderOpt.isEmpty()) return Map.<String, Object>of("success", false, "message", "Sender not found");
        if (receiverOpt.isEmpty()) return Map.<String, Object>of("success", false, "message", "UPI ID not found: " + receiverUpi);

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        if (sender.getBalance() < amount)
            return Map.<String, Object>of("success", false, "message", "Insufficient balance");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction txn = new Transaction();
        txn.setSenderId(senderId);
        txn.setReceiverId(receiverUpi);
        txn.setAmount(amount);
        txn.setType("UPI");
        txn.setStatus("SUCCESS");
        txn.setMode("UPI");
        txn.setNote(note != null ? note : "");
        transactionRepository.save(txn);

        return Map.<String, Object>of(
                "success", true,
                "message", "₹" + amount + " sent via UPI to " + receiverUpi,
                "newBalance", sender.getBalance()
        );
    }

    @Transactional
    public Map<String, Object> deposit(Long userId, Double amount) {
        String paymentId = "PAY-" + System.currentTimeMillis();
        if (amount <= 0) return Map.<String, Object>of("success", false, "message", "Amount must be greater than 0");

        return userRepository.findById(userId).map(user -> {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);

            Transaction txn = new Transaction();
            txn.setSenderId(userId);
            txn.setReceiverId("SELF");
            txn.setAmount(amount);
            txn.setType("DEPOSIT");
            txn.setStatus("SUCCESS");
            txn.setMode("BANK");
            txn.setNote(paymentId);
            transactionRepository.save(txn);

            return Map.<String, Object>of(
                    "success", true,
                    "message", "₹" + amount + " deposited successfully",
                    "paymentId", paymentId,
                    "newBalance", user.getBalance()
            );
        }).orElse(Map.<String, Object>of("success", false, "message", "User not found"));
    }

    public Map<String, Object> logCrypto(Long userId, String txHash, Double amount, String note) {
        Transaction txn = new Transaction();
        txn.setSenderId(userId);
        txn.setReceiverId(txHash);
        txn.setAmount(amount);
        txn.setType("CRYPTO");
        txn.setStatus("SUCCESS");
        txn.setMode("CRYPTO");
        txn.setNote(note != null ? note : "MetaMask transaction");
        transactionRepository.save(txn);
        return Map.<String, Object>of("success", true, "message", "Crypto transaction recorded");
    }

    public List<Transaction> getHistory(Long userId) {
        return transactionRepository.findAllByUser(userId);
    }

    public Map<String, Object> getStats(Long userId) {
        long total = transactionRepository.countBySenderId(userId);
        Double totalSent = transactionRepository.sumSentByUser(userId);
        double sentValue = (totalSent != null) ? totalSent : 0.0;

        return Map.<String, Object>of(
                "totalTransactions", total,
                "totalSent", sentValue
        );
    }
}