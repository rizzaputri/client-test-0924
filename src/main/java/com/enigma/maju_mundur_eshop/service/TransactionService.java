package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.dto.request.TransactionRequest;
import com.enigma.maju_mundur_eshop.dto.request.UpdatePaymentStatusRequest;
import com.enigma.maju_mundur_eshop.dto.response.TransactionResponse;
import com.enigma.maju_mundur_eshop.entity.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    Transaction getById(String id);
    List<TransactionResponse> getAllTransactions();
    void updateStatus(UpdatePaymentStatusRequest status);
}
