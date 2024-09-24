package com.enigma.maju_mundur_eshop.dto.request;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMerchantRequest {
    private String shopName;

    @Email(message = ConstantMessage.INPUT_VALIDATION)
    private String email;

    private String address;

    private String snsLink;

    private String username;

    private String password;
}
