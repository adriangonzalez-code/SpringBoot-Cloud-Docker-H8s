package com.driagon.microservices.app.controllers;

import com.driagon.microservices.app.models.PaymentRequest;
import com.driagon.microservices.app.models.PaymentResponse;
import com.driagon.microservices.app.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService service;

    @PostMapping()
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request) {
        return new ResponseEntity<>(this.service.doPayment(request), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId) {
        return new ResponseEntity<>(this.service.getPaymentDetailsByOrderId(orderId), HttpStatus.OK);
    }
}