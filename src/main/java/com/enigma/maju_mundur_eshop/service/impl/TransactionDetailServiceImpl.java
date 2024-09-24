package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.entity.TransactionDetail;
import com.enigma.maju_mundur_eshop.repository.TransactionDetailRepository;
import com.enigma.maju_mundur_eshop.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }
}
