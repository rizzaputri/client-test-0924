package com.enigma.maju_mundur_eshop.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
	@JsonProperty("transaction_details")
	private PaymentTransactionDetailRequest transactionDetailRequest;

	@JsonProperty("item_details")
	private List<PaymentItemDetailRequest> itemDetails;

	@JsonProperty("customer_details")
	private PaymentCustomerDetailRequest customerDetails;

	@JsonProperty("enabled_payments")
	private List<String> enabledPayments;
}