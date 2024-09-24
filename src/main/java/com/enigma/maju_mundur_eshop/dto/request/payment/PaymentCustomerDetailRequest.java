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
public class PaymentCustomerDetailRequest {
	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("phone")
	private String phone;
}
