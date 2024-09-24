package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.request.UpdateMerchantRequest;
import com.enigma.maju_mundur_eshop.dto.response.PrivateMerchantResponse;
import com.enigma.maju_mundur_eshop.dto.response.PublicMerchantResponse;
import com.enigma.maju_mundur_eshop.entity.Merchant;

import java.util.List;

public interface MerchantService {
    void createMerchant(Merchant merchant);
    Merchant getById(String id);
    Merchant getByUsername(String username);
    PrivateMerchantResponse getMerchantById();
    List<PublicMerchantResponse> getAllMerchants();
    PrivateMerchantResponse updateMerchant(UpdateMerchantRequest request);
    void deleteMerchant(String id);
}
