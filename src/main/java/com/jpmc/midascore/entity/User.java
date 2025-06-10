package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionRecord> sentTransactions;
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionRecord> receivedTransactions;
    
    public User() {}
    
    public User(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    
    public List<TransactionRecord> getSentTransactions() { return sentTransactions; }
    public void setSentTransactions(List<TransactionRecord> sentTransactions) { this.sentTransactions = sentTransactions; }
    
    public List<TransactionRecord> getReceivedTransactions() { return receivedTransactions; }
    public void setReceivedTransactions(List<TransactionRecord> receivedTransactions) { this.receivedTransactions = receivedTransactions; }
}
