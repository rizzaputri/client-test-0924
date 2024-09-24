package com.enigma.maju_mundur_eshop.repository;

import com.enigma.maju_mundur_eshop.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
}
