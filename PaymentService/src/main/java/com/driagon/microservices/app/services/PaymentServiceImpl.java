package com.driagon.microservices.app.services;

import com.driagon.microservices.app.entities.TransactionDetail;
import com.driagon.microservices.app.models.PaymentRequest;
import com.driagon.microservices.app.repositories.ITransactionDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private ITransactionDetailRepository repository;

    @Override
    public long doPayment(PaymentRequest request) {
        log.info("Recording Payment Details: {}", request);

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .paymentDate(Instant.now())
                .paymentMode(request.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(request.getOrderId())
                .referenceNumber(request.getReferenceNumber())
                .amount(request.getAmount())
                .build();

        this.repository.save(transactionDetail);

        log.info("Transaction Completed with Id: {}", transactionDetail.getId());

        return transactionDetail.getId();
    }
}