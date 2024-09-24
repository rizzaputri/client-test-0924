package com.enigma.maju_mundur_eshop.repository;

import com.enigma.maju_mundur_eshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByUserAccountUsername(String username);

    @Query(value = "SELECT c.* FROM m_customer c " +
            "JOIN t_transaction tx ON tx.customer_id = c.id " +
            "JOIN t_transaction_detail td ON td.transaction_id = tx.id " +
            "JOIN m_product p ON p.id = td.product_id " +
            "JOIN m_merchant m ON m.id = p.merchant_id " +
            "WHERE p.merchant_id = :id",
            nativeQuery = true)
    List<Customer> findAllByMerchantId(@Param("id") String id);
}
