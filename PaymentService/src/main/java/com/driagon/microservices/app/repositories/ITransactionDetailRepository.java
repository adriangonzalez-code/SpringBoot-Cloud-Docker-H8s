package com.driagon.microservices.app.repositories;

import com.driagon.microservices.app.entities.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {


}