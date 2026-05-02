package com.hybridpay.service;

import com.hybridpay.entity.User;
import com.hybridpay.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> register(String name, String email, String password) {
        email = email.toLowerCase();
        if(!email.endsWith("@hybridpay.com")) return Map.<String,Object>of("success",false,"message","Username must end with @hybridpay.com");
        if(password.length()<8) return Map.<String,Object>of("success",false,"message","Password must be at least 8 characters");
        if (userRepository.existsByEmail(email)) {
            return Map.<String, Object>of("success", false, "message", "Email already registered");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setBalance(10000.0);   
        userRepository.save(user);
        return Map.<String, Object>of("success", true, "message", "Registration successful! You got ₹10,000 welcome bonus.");
    }

    public Map<String, Object> login(String email, String password) {
        email = email.toLowerCase();
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty() || !opt.get().getPassword().equals(password)) {
            return Map.<String, Object>of("success", false, "message", "Invalid email or password");
        }
        User user = opt.get();
        return Map.<String, Object>of(
                "success", true,
                "userId", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "balance", user.getBalance(),
                "upiId", user.getUpiId() != null ? user.getUpiId() : "",
                "walletAddress", user.getWalletAddress() != null ? user.getWalletAddress() : ""
        );
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Map<String, Object> linkUpi(Long userId, String upiId) {
        return userRepository.findById(userId).map(user -> {
            user.setUpiId(upiId);
            userRepository.save(user);
            return Map.<String, Object>of("success", true, "message", "UPI ID linked: " + upiId);
        }).orElse(Map.<String, Object>of("success", false, "message", "User not found"));
    }

    public Map<String, Object> linkWallet(Long userId, String walletAddress) {
        return userRepository.findById(userId).map(user -> {
            user.setWalletAddress(walletAddress);
            userRepository.save(user);
            return Map.<String, Object>of("success", true, "message", "Wallet linked: " + walletAddress);
        }).orElse(Map.<String, Object>of("success", false, "message", "User not found"));
    }
}