package com.enigma.maju_mundur_eshop.dto.request;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = ConstantMessage.NOT_BLANK_VALIDATION)
    private String id;

    private String productName;

    private Long unitPrice;

    private Integer stock;
}
