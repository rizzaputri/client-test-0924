package com.enigma.maju_mundur_eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicMerchantResponse {
    private String shopName;
    private String address;
    private String snsLink;
}
