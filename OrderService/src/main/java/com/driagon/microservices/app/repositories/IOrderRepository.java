package com.driagon.microservices.app.repositories;

import com.driagon.microservices.app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}