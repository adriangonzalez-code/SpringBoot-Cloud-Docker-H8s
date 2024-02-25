package com.driagon.microservices.app.services;

import com.driagon.microservices.app.entities.Product;
import com.driagon.microservices.app.exceptions.ProductServiceCustomException;
import com.driagon.microservices.app.models.ProductRequest;
import com.driagon.microservices.app.models.ProductResponse;
import com.driagon.microservices.app.repositories.IProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Override
    public long addProduct(ProductRequest request) {
        log.info("Adding Product");

        Product product = Product.builder()
                .productName(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        this.repository.save(product);

        log.info("Product created");
        return product.getId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("Get the product for id: {}", id);

        Product product = this.repository.findById(id).orElseThrow(() -> new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));

        ProductResponse response = new ProductResponse();
        copyProperties(product, response);

        return response;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce {} quantity for Id: {}", quantity, productId);

        Product product = this.repository.findById(productId).orElseThrow(() -> new ProductServiceCustomException("Product with given Id is not found", "PRODUCT_NOT_FOUND"));

        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Product does not have sufficient quantity", "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity() - quantity);
        this.repository.save(product);

        log.info("Product quantity updated successfully");
    }
}