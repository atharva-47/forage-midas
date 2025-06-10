package com.jpmc.midascore.foundation;

public class Transaction {
    private String id;
    private Integer senderId;
    private Integer recipientId;
    private Float amount;
    
    public Transaction() {}
    
    public Transaction(Long senderId, Long recipientId, Float amount) {
        this.senderId = senderId != null ? senderId.intValue() : null;
        this.recipientId = recipientId != null ? recipientId.intValue() : null;
        this.amount = amount;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Integer getSenderId() { return senderId; }
    public void setSenderId(Integer senderId) { this.senderId = senderId; }
    
    public Integer getRecipientId() { return recipientId; }
    public void setRecipientId(Integer recipientId) { this.recipientId = recipientId; }
    
    public Float getAmount() { return amount; }
    public void setAmount(Float amount) { this.amount = amount; }
    
    public Double getAmountAsDouble() {
        return amount != null ? amount.doubleValue() : 0.0;
    }
    
    @Override
    public String toString() {
        return "Transaction {senderId=" + senderId + ", recipientId=" + recipientId + ", amount=" + amount + "}";
    }
}
