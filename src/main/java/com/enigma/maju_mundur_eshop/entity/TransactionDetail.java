package com.enigma.maju_mundur_eshop.entity;

import com.enigma.maju_mundur_eshop.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.TRANSACTION_DETAIL)
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(updatable = false, nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
