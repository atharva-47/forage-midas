package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionRecordRepository transactionRecordRepository;
    
    @Autowired
    private IncentiveService incentiveService;
    
    @Transactional
    public boolean processTransaction(Transaction transaction) {
        try {
            // Validate sender
            Optional<User> senderOpt = userRepository.findById(transaction.getSenderId().longValue());
            if (!senderOpt.isPresent()) {
                System.out.println("Invalid sender ID: " + transaction.getSenderId());
                return false;
            }
            
            // Validate recipient
            Optional<User> recipientOpt = userRepository.findById(transaction.getRecipientId().longValue());
            if (!recipientOpt.isPresent()) {
                System.out.println("Invalid recipient ID: " + transaction.getRecipientId());
                return false;
            }
            
            User sender = senderOpt.get();
            User recipient = recipientOpt.get();
            BigDecimal amount = BigDecimal.valueOf(transaction.getAmountAsDouble());
            
            // Check if sender has sufficient balance
            if (sender.getBalance().compareTo(amount) < 0) {
                System.out.println("Insufficient balance for sender: " + sender.getUsername() + 
                                 " (balance: " + sender.getBalance() + ", required: " + amount + ")");
                return false;
            }
            
            // Get incentive from API
            BigDecimal incentive = incentiveService.getIncentive(transaction);
            
            // Process the transaction
            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount).add(incentive));
            
            // Save updated users
            userRepository.save(sender);
            userRepository.save(recipient);
            
            // Create and save transaction record with incentive
            TransactionRecord record = new TransactionRecord(sender, recipient, amount, incentive);
            transactionRecordRepository.save(record);
            
            System.out.println("Transaction processed successfully: " + 
                             sender.getUsername() + " -> " + recipient.getUsername() + 
                             " amount: " + amount + " incentive: " + incentive);
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Error processing transaction: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public BigDecimal getUserBalance(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.map(User::getBalance).orElse(BigDecimal.ZERO);
    }
}
