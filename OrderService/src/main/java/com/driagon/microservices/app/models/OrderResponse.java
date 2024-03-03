package com.driagon.microservices.app.models;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderResponse {

    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetail productDetail;
    private PaymentDetails paymentDetails;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ProductDetail {

        private long id;
        private String productName;
        private long quantity;
        private long price;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class PaymentDetails {

        private Long paymentId;
        private String status;
        private PaymentMode paymentMode;
        private Instant paymentDate;
    }
}