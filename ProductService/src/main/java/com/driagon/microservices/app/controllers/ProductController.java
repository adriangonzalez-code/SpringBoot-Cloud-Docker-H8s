package com.driagon.microservices.app.controllers;

import com.driagon.microservices.app.models.ProductRequest;
import com.driagon.microservices.app.models.ProductResponse;
import com.driagon.microservices.app.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @PostMapping()
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {
        long productId = this.service.addProduct(request);

        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id) {
        ProductResponse response = this.service.getProductById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {
        this.service.reduceQuantity(productId, quantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}