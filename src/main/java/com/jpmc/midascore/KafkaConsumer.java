package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "${general.kafka-topic}")
    public void consume(Transaction transaction) {
        System.out.println("Received transaction: " + transaction);
        transactionService.processTransaction(transaction);
    }
}