package com.enigma.maju_mundur_eshop.entity;

import com.enigma.maju_mundur_eshop.constant.ConstantTable;
import com.enigma.maju_mundur_eshop.constant.RewardType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.REWARD)
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private RewardType type;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
