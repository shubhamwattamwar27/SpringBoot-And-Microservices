package com.example.orderservice.service;

import com.example.orderservice.common.Payment;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.TransactionRequest;
import com.example.orderservice.entity.TransactionResponse;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
@Slf4j
public class OrderService {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_ENDPOINT_URL ;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {

        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        log.info("OrderService: {Transaction request } : " ,new ObjectMapper().writeValueAsString(transactionRequest));
        //post call to payment service
        Payment paymentResponse = restTemplate.postForObject(PAYMENT_ENDPOINT_URL,payment,Payment.class);
        log.info("OrderService: {payment service response } : " ,new ObjectMapper().writeValueAsString(paymentResponse));
        String messsage = paymentResponse.getPaymentStatus().equals("Success") ? "Payment processed Successfully and order placed" :"Payment processing failed";
        order =  orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),messsage);
    }
}
