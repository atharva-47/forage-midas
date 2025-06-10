package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.model.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class BalanceController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return new Balance(userOpt.get().getBalance());
        } else {
            return new Balance(BigDecimal.ZERO);
        }
    }
}
