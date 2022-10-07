package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        log.info(" PaymentService : {doPayment} : "+ new ObjectMapper().writeValueAsString(payment));
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing(){
        return new Random().nextBoolean() ?"Success":"false";
    }

    public Payment findPaymentHistoryByOrderId(int oderId) throws JsonProcessingException {
        Payment payment =  paymentRepository.findByOrderId(oderId);
        log.info(" PaymentService : {doPayment} : "+ new ObjectMapper().writeValueAsString(payment));
        return payment;

    }
}
