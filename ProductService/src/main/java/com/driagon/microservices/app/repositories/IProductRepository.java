package com.driagon.microservices.app.repositories;

import com.driagon.microservices.app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}