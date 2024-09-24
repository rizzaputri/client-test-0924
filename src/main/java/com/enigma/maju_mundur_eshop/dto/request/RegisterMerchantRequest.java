package com.enigma.maju_mundur_eshop.dto.request;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMerchantRequest {
    @NotBlank(message = ConstantMessage.NOT_BLANK_VALIDATION)
    private String username;

    @NotBlank(message = ConstantMessage.NOT_BLANK_VALIDATION)
    private String password;

    @NotBlank(message = ConstantMessage.NOT_BLANK_VALIDATION)
    private String shopName;

    @NotNull(message = ConstantMessage.NOT_BLANK_VALIDATION)
    @Email(message = ConstantMessage.INPUT_VALIDATION)
    private String email;
}
