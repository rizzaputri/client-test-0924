package com.enigma.maju_mundur_eshop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String id;
    private String token;

    @JsonProperty("redirect_url")
    private String redirectUrl;

    @JsonProperty("transaction_status")
    private String transactionStatus;
}
