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
@Table(name = ConstantTable.MERCHANT)
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "shop_name", unique = true, nullable = false)
    private String shopName;

    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    @Column(name = "sns_link")
    private String snsLink;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;
}
