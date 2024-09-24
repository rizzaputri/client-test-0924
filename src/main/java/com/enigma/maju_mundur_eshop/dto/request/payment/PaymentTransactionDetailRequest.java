package com.enigma.maju_mundur_eshop.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransactionDetailRequest {
	@JsonProperty("order_id")
	private String orderId;

	@JsonProperty("gross_amount")
	private Long grossAmount;
}
