package com.enigma.maju_mundur_eshop.dto.request;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import jakarta.validation.constraints.Min;
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
public class TransactionDetailRequest {
    @NotBlank(message = ConstantMessage.NOT_BLANK_VALIDATION)
    private String productId;

    @NotNull(message = ConstantMessage.NOT_BLANK_VALIDATION)
    @Min(value = 0, message = ConstantMessage.GREATER_THAN_VALIDATION)
    private Integer quantity;
}
