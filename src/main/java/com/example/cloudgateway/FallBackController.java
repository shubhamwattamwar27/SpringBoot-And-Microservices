package com.example.cloudgateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/orderFallBack")
    public Mono<String> orderService(){
        return Mono.just("Order Service taking too much time to respond");
    }

    @RequestMapping("/paymentFallBack")
    public Mono<String> paymentService(){
        return Mono.just("Payment Service taking too much time to respond");
    }
}
