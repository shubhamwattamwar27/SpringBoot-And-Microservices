package com.example.orderservice.service;

import com.example.orderservice.common.Payment;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.TransactionRequest;
import com.example.orderservice.entity.TransactionResponse;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest){

        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment",payment,Payment.class);
        String messsage = paymentResponse.getPaymentStatus().equals("Success") ? "Payment processed Successfully and order placed" :"Payment processing failed";
        order =  orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),messsage);
    }
}
