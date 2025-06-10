package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducer {

    @Value("${general.kafka-topic}")
    private String topic;
    
    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @PostConstruct
    public void init() {
        this.kafkaTemplate = new KafkaTemplate<>(producerFactory());
    }

    private ProducerFactory<String, Transaction> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    public void send(Transaction transaction) {
        kafkaTemplate.send(topic, transaction);
    }

    public void send(String transactionLine) {
        Transaction transaction = parseTransactionFromString(transactionLine);
        send(transaction);
    }

    private Transaction parseTransactionFromString(String line) {
        // Assuming format: senderId,recipientId,amount
        String[] parts = line.split(",");
        Transaction transaction = new Transaction();
        
        if (parts.length >= 3) {
            try {
                transaction.setSenderId(Integer.parseInt(parts[0].trim()));
                transaction.setRecipientId(Integer.parseInt(parts[1].trim()));
                transaction.setAmount(Float.parseFloat(parts[2].trim()));
            } catch (NumberFormatException e) {
                System.err.println("Error parsing transaction: " + line + " - " + e.getMessage());
                transaction.setAmount(0.0f);
            }
        }
        
        return transaction;
    }
}
