package com.driagon.microservices.app.services;

import com.driagon.microservices.app.models.PaymentRequest;

public interface IPaymentService {
    long doPayment(PaymentRequest request);
}