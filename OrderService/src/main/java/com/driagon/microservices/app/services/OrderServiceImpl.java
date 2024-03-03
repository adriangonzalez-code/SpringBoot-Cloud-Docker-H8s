package com.driagon.microservices.app.services;

import com.driagon.microservices.app.entities.Order;
import com.driagon.microservices.app.exceptions.CustomException;
import com.driagon.microservices.app.externals.clients.PaymentService;
import com.driagon.microservices.app.externals.clients.ProductService;
import com.driagon.microservices.app.externals.requests.PaymentRequest;
import com.driagon.microservices.app.externals.responses.PaymentResponse;
import com.driagon.microservices.app.models.OrderRequest;
import com.driagon.microservices.app.models.OrderResponse;
import com.driagon.microservices.app.externals.responses.ProductResponse;
import com.driagon.microservices.app.repositories.IOrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest request) {
        // Order Entity -> Save the data with Status Order created
        // Product Service -> Blocks products (Reduce the quantity)
        // Payment Service -> Payments -> Success -> COMPLETED, Else
        // CANCELLED
        log.info("Placing Order Request: {}", request);

        this.productService.reduceQuantity(request.getProductId(), request.getQuantity());

        log.info("Creating order with status CREATED");

        Order order = Order.builder()
                .amount(request.getTotalAmount())
                .orderStatus("CREATED")
                .productId(request.getProductId())
                .orderDate(Instant.now())
                .quantity(request.getQuantity())
                .build();

        order = this.repository.save(order);

        log.info("Calling to Payment Service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(request.getPaymentMode())
                .amount(request.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            this.paymentService.doPayment(paymentRequest);
            log.info("Payment Done Successfully. Changing the status to PLACED");

            orderStatus = "PLACED";
        } catch (HttpStatusCodeException ex) {
            log.error("Error occurred in Payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        this.repository.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        log.info("Get order details for Order Id: {}", orderId);

        Order order = this.repository.findById(orderId).orElseThrow(() -> new CustomException("Order not found for the order id: " + orderId, "NOT_FOUND", 404));

        log.info("Invoking Product Service to fetch the product for id: {}", order.getId());

        ProductResponse productResponse = this.restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class);

        OrderResponse.ProductDetail productDetail = OrderResponse.ProductDetail.builder()
                .productName(productResponse.getProductName())
                .id(productResponse.getId())
                .build();

        log.info("Getting Payment information from the Payment Service");
        PaymentResponse paymentResponse = this.restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/" + orderId, PaymentResponse.class);

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .status(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse response = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetail(productDetail)
                .paymentDetails(paymentDetails)
                .build();

        return response;
    }
}