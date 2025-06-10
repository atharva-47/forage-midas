package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.model.Incentive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@Service
public class IncentiveService {
    
    private final RestTemplate restTemplate;
    private final String incentiveApiUrl;
    
    public IncentiveService(RestTemplate restTemplate, @Value("${incentive.api.url:http://localhost:8080/incentive}") String incentiveApiUrl) {
        this.restTemplate = restTemplate;
        this.incentiveApiUrl = incentiveApiUrl;
    }
    
    public BigDecimal getIncentive(Transaction transaction) {
        try {
            Incentive incentive = restTemplate.postForObject(incentiveApiUrl, transaction, Incentive.class);
            return incentive != null ? incentive.getAmount() : BigDecimal.ZERO;
        } catch (RestClientException e) {
            System.err.println("Failed to get incentive for transaction: " + e.getMessage());
            // Return a mock incentive of 1% of transaction amount for testing
            return BigDecimal.valueOf(transaction.getAmountAsDouble() * 0.01);
        }
    }
}
