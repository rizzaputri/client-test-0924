package com.enigma.maju_mundur_eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private LocalDateTime transactionDate;
    private Long totalPrice;
    private String customerId;
    private List<TransactionDetailResponse> transactionDetails;
    private PaymentResponse payment;
}
