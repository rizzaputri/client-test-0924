package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String id);
    UserAccount getByContext();
}
