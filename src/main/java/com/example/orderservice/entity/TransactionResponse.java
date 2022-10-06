package com.example.orderservice.entity;

import com.example.orderservice.common.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponse {

    private Order order;
    private double amount;
    private String transactionId;
    private String message;
}
