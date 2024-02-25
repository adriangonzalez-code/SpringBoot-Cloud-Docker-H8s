package com.driagon.microservices.app.services;

import com.driagon.microservices.app.models.OrderRequest;

public interface IOrderService {

    long placeOrder(OrderRequest request);
}