package com.enigma.maju_mundur_eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateCustomerResponse {
    private String id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private Integer point;
    private String userAccountId;
    private String username;
}
