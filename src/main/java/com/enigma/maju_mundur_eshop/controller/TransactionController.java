package com.enigma.maju_mundur_eshop.controller;

import com.enigma.maju_mundur_eshop.constant.APIUrl;
import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.dto.request.TransactionRequest;
import com.enigma.maju_mundur_eshop.dto.request.UpdatePaymentStatusRequest;
import com.enigma.maju_mundur_eshop.dto.response.CommonResponse;
import com.enigma.maju_mundur_eshop.dto.response.TransactionResponse;
import com.enigma.maju_mundur_eshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(
            @RequestBody TransactionRequest request
    ) {
        TransactionResponse transaction = transactionService.createTransaction(request);
        CommonResponse<TransactionResponse> response = CommonResponse
                .<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ConstantMessage.CREATE_SUCCESS + transaction.getId())
                .data(transaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransaction() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        CommonResponse<List<TransactionResponse>> response = CommonResponse
                .<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.FETCH_SUCCESS + "all data")
                .data(transactions)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = APIUrl.PAYMENT_STATUS_API)
    public ResponseEntity<CommonResponse<?>> updatePaymentStatus(
            @RequestBody Map<String, Object> request
    ) {
        UpdatePaymentStatusRequest paymentStatusRequest = UpdatePaymentStatusRequest.builder()
                .orderId(request.get("order_id").toString())
                .paymentStatus(request.get("transaction_status").toString())
                .build();
        transactionService.updateStatus(paymentStatusRequest);

        CommonResponse<?> response = CommonResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.UPDATE_SUCCESS + "payment status")
                .build();
        return ResponseEntity.ok(response);
    }
}
