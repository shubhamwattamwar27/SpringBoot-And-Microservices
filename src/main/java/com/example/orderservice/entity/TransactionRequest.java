package com.example.orderservice.entity;

import com.example.orderservice.common.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequest {

    private Order order;
    private Payment payment;
}
