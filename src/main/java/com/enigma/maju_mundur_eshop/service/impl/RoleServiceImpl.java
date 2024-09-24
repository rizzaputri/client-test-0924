package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.UserRole;
import com.enigma.maju_mundur_eshop.entity.Role;
import com.enigma.maju_mundur_eshop.repository.RoleRepository;
import com.enigma.maju_mundur_eshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role).orElseGet(
                () -> roleRepository.saveAndFlush(Role.builder()
                                .role(role)
                                .build()
                )
        );
    }
}
