package com.example.atm.controller;

import com.example.atm.entity.Transaction;
import com.example.atm.entity.User;
import com.example.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atm")
public class AtmController {

    @Autowired
    private AtmService atmService;

    // 1️⃣ User Authentication
    @PostMapping("/login")
    public User login(@RequestParam Long userId,
                      @RequestParam Integer pin) {
        return atmService.authenticate(userId, pin);
    }

    // 2️⃣ Balance Inquiry
    @GetMapping("/balance/{userId}")
    public Double getBalance(@PathVariable Long userId) {
        return atmService.checkBalance(userId);
    }

    // 3️⃣ Cash Deposit
    @PostMapping("/deposit")
    public String deposit(@RequestParam Long userId,
                          @RequestParam Double amount) {
        return atmService.deposit(userId, amount);
    }

    // 4️⃣ Cash Withdrawal
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long userId,
                           @RequestParam Double amount) {
        return atmService.withdraw(userId, amount);
    }

    // 5️⃣ Transaction History
    @GetMapping("/transactions/{userId}")
    public List<Transaction> getTransactions(@PathVariable Long userId) {
        return atmService.getTransactionHistory(userId);
    }
}