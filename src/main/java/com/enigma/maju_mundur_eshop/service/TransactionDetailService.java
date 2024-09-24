package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails);
}
