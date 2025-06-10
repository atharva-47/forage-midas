package com.jpmc.midascore.config;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Create initial users if they don't exist
        if (userRepository.count() == 0) {
            createInitialUsers();
        }
    }
    
    private void createInitialUsers() {
        // Create users with IDs 1-10 and some initial balances
        for (int i = 1; i <= 10; i++) {
            User user = new User("user" + i, new BigDecimal("1000.00"));
            userRepository.save(user);
        }
        
        // Create specific test users including waldorf
        User waldorf = new User("waldorf", new BigDecimal("1000.00"));
        userRepository.save(waldorf);
        
        System.out.println("Initial users created with balances of 1000.00 each");
    }
}
