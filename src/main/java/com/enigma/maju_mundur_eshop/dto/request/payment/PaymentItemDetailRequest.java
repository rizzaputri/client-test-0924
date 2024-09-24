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
public class PaymentItemDetailRequest {
	@JsonProperty("name")
	private String name;

	@JsonProperty("price")
	private Long price;

	@JsonProperty("quantity")
	private Integer quantity;
}
