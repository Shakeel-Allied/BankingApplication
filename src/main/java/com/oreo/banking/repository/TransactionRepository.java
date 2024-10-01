package com.oreo.banking.repository;


import com.oreo.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Additional query methods if needed
}
