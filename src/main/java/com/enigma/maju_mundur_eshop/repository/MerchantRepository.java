package com.enigma.maju_mundur_eshop.repository;

import com.enigma.maju_mundur_eshop.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Merchant findByUserAccountUsername(String username);
}
