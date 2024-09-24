package com.enigma.maju_mundur_eshop.service;

import com.enigma.maju_mundur_eshop.entity.Payment;
import com.enigma.maju_mundur_eshop.entity.Transaction;

public interface PaymentService {
    Payment createPayment(Transaction transaction);
}
