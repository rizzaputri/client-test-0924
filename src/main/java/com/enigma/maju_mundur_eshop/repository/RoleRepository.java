package com.enigma.maju_mundur_eshop.repository;

import com.enigma.maju_mundur_eshop.constant.UserRole;
import com.enigma.maju_mundur_eshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
