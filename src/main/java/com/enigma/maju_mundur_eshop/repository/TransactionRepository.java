package com.enigma.maju_mundur_eshop.repository;

import com.enigma.maju_mundur_eshop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
