package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.request.LoginRequest;
import com.enigma.maju_mundur_eshop.dto.request.RegisterCustomerRequest;
import com.enigma.maju_mundur_eshop.dto.request.RegisterMerchantRequest;
import com.enigma.maju_mundur_eshop.dto.response.LoginResponse;
import com.enigma.maju_mundur_eshop.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerMerchant(RegisterMerchantRequest request);
    RegisterResponse registerCustomer(RegisterCustomerRequest request);
    LoginResponse login(LoginRequest loginRequest);
}
