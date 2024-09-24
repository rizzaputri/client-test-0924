package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.response.JWTClaims;
import com.enigma.maju_mundur_eshop.entity.UserAccount;

public interface JWTService {
    String generateToken(UserAccount userAccount);
    boolean validateToken(String token);
    JWTClaims getClaimsByToken(String token);
}
