package com.enigma.maju_mundur_eshop.repository;

import com.enigma.maju_mundur_eshop.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, String> {
    Optional<List<Reward>> findAllByCustomerId(String id);
}
