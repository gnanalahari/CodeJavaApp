package com.example.atm.service;

import com.example.atm.entity.Transaction;
import com.example.atm.entity.User;
import com.example.atm.repository.TransactionRepository;
import com.example.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtmService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // 1️⃣ User Authentication
    public User authenticate(Long userId, Integer pin) {
        return userRepository.findByIdAndPin(userId, pin)
                .orElseThrow(() -> new RuntimeException("Invalid User ID or PIN"));
    }

    // 2️⃣ Balance Inquiry
    public Double checkBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBalance();
    }

    // 3️⃣ Cash Deposit
    public String deposit(Long userId, Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Deposit amount must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Transaction transaction = new Transaction(user, "DEPOSIT", amount);
        transactionRepository.save(transaction);

        return "Deposit successful. Updated balance: " + user.getBalance();
    }

    // 4️⃣ Cash Withdrawal
    public String withdraw(Long userId, Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Withdrawal amount must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction transaction = new Transaction(user, "WITHDRAW", amount);
        transactionRepository.save(transaction);

        return "Withdrawal successful. Updated balance: " + user.getBalance();
    }

    // 5️⃣ Transaction History
    public List<Transaction> getTransactionHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }
}
