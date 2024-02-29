package com.driagon.microservices.app.externals.clients;

import com.driagon.microservices.app.externals.requests.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping()
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);
}