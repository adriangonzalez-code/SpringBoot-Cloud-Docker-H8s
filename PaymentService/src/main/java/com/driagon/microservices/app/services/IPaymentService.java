package com.driagon.microservices.app.services;

import com.driagon.microservices.app.models.PaymentRequest;
import com.driagon.microservices.app.models.PaymentResponse;

public interface IPaymentService {
    long doPayment(PaymentRequest request);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}