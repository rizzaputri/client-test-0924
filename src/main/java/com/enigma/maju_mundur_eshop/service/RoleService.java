package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.constant.UserRole;
import com.enigma.maju_mundur_eshop.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
