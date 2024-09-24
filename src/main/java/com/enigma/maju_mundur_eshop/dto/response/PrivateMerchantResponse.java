package com.enigma.maju_mundur_eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMerchantResponse {
    private String id;
    private String shopName;
    private String email;
    private String address;
    private String snsLink;
    private String userAccountId;
    private String username;
}
