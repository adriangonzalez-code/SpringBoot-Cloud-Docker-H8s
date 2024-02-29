package com.driagon.microservices.app.services;

import com.driagon.microservices.app.models.OrderRequest;
import com.driagon.microservices.app.models.OrderResponse;

public interface IOrderService {

    long placeOrder(OrderRequest request);

    OrderResponse getOrderDetails(long orderId);
}