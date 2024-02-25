package com.driagon.microservices.app.services;

import com.driagon.microservices.app.models.ProductRequest;
import com.driagon.microservices.app.models.ProductResponse;

public interface IProductService {

    long addProduct(ProductRequest request);

    ProductResponse getProductById(long id);

    void reduceQuantity(long productId, long quantity);
}