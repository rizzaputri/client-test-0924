package com.enigma.maju_mundur_eshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusRequest {
    private String orderId;
    private String paymentStatus;
}
