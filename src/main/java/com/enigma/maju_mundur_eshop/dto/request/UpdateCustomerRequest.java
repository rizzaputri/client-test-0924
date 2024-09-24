package com.enigma.maju_mundur_eshop.dto.request;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    private String name;

    @Email(message = ConstantMessage.INPUT_VALIDATION)
    private String email;

    private String address;

    @Pattern(regexp = "^(?:\\+62|62|0)[2-9]\\d{7,11}$", message = ConstantMessage.INPUT_VALIDATION)
    private String phoneNumber;

    private String username;

    private String password;
}
