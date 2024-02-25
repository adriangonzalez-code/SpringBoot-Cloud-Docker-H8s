package com.driagon.microservices.app.services;

import com.driagon.microservices.app.entities.Order;
import com.driagon.microservices.app.externals.clients.ProductService;
import com.driagon.microservices.app.models.OrderRequest;
import com.driagon.microservices.app.repositories.IOrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private ProductService productService;

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

        log.info("Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }
}