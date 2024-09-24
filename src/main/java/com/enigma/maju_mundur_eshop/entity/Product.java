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
@Table(name = ConstantTable.PRODUCT)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "unit_price", nullable = false)
    private Long unitPrice;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
