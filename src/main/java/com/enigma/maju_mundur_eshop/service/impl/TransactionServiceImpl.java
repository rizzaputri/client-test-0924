package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.ConstantMessage;
import com.enigma.maju_mundur_eshop.constant.RewardType;
import com.enigma.maju_mundur_eshop.constant.TransactionStatus;
import com.enigma.maju_mundur_eshop.dto.request.TransactionRequest;
import com.enigma.maju_mundur_eshop.dto.request.UpdatePaymentStatusRequest;
import com.enigma.maju_mundur_eshop.dto.response.PaymentResponse;
import com.enigma.maju_mundur_eshop.dto.response.TransactionDetailResponse;
import com.enigma.maju_mundur_eshop.dto.response.TransactionResponse;
import com.enigma.maju_mundur_eshop.entity.*;
import com.enigma.maju_mundur_eshop.repository.TransactionRepository;
import com.enigma.maju_mundur_eshop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private final TransactionDetailService transactionDetailService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final RewardService rewardService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        Customer customer = customerService.getByUsername(userService.getByContext().getUsername());

        // Create Transaction
        List<TransactionDetail> transactionDetails = new ArrayList<>();
        Transaction transaction = Transaction.builder()
                .transactionDate(LocalDateTime.now())
                .customer(customer)
                .transactionDetails(transactionDetails)
                .build();
        transactionRepository.saveAndFlush(transaction);

        // Create Transaction Detail and Set
        request.getRequests().forEach(detailRequest -> {
                    Product product = productService.getById(detailRequest.getProductId());

                    if (product.getStock() - detailRequest.getQuantity() < 0) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstantMessage.OUT_OF_STOCK);
                    }
                    product.setStock(product.getStock() - detailRequest.getQuantity());

                    TransactionDetail transactionDetail = TransactionDetail.builder()
                            .quantity(detailRequest.getQuantity())
                            .price(detailRequest.getQuantity() * product.getUnitPrice())
                            .product(product)
                            .transaction(transaction)
                            .build();
                    transactionDetails.add(transactionDetail);
                });
        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        // Set total price
        Long totalPrice = transaction.getTransactionDetails().stream().mapToLong(TransactionDetail::getPrice).sum();
        transaction.setTotalPrice(totalPrice);
        transactionRepository.saveAndFlush(transaction);

        // Set Payment
        Payment payment = paymentService.createPayment(transaction);
        transaction.setPayment(payment);

        // Create Response
        // 1. transactionDetails
        List<TransactionDetailResponse> transactionDetailResponses = transactionDetails.stream()
                .map(detailResponse -> {
                    return TransactionDetailResponse.builder()
                            .id(detailResponse.getId())
                            .quantity(detailResponse.getQuantity())
                            .price(detailResponse.getPrice())
                            .productId(detailResponse.getProduct().getId())
                            .build();
                }).toList();
        // 2. payment
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getTransactionStatus().toString())
                .build();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionDate(transaction.getTransactionDate())
                .totalPrice(transaction.getTotalPrice())
                .customerId(customer.getId())
                .transactionDetails(transactionDetailResponses)
                .payment(paymentResponse)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Transaction getById(String id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantMessage.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transaction -> {
                    List<TransactionDetailResponse> transactionDetailResponses = transaction.getTransactionDetails().stream()
                            .map(detailResponse -> {
                                return TransactionDetailResponse.builder()
                                        .id(detailResponse.getId())
                                        .quantity(detailResponse.getQuantity())
                                        .price(detailResponse.getPrice())
                                        .productId(detailResponse.getProduct().getId())
                                        .build();
                            }).toList();

                    PaymentResponse paymentResponse = PaymentResponse.builder()
                            .id(transaction.getPayment().getId())
                            .token(transaction.getPayment().getToken())
                            .redirectUrl(transaction.getPayment().getRedirectUrl())
                            .transactionStatus(transaction.getPayment().getTransactionStatus().toString())
                            .build();

                    return TransactionResponse.builder()
                            .id(transaction.getId())
                            .transactionDate(transaction.getTransactionDate())
                            .totalPrice(transaction.getTotalPrice())
                            .customerId(transaction.getCustomer().getId())
                            .transactionDetails(transactionDetailResponses)
                            .payment(paymentResponse)
                            .build();
                }).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(UpdatePaymentStatusRequest status) {
        Transaction transaction = getById(status.getOrderId());

        // Update payment
        Payment payment = transaction.getPayment();
        payment.setTransactionStatus(TransactionStatus.valueOf(status.getPaymentStatus()));

        // Add point if payment success
        if (status.getPaymentStatus().equals("settlement")) {
            Customer customer = customerService.getById(transaction.getCustomer().getId());
            Integer point = customer.getPoint() + 1;
            customer.setPoint(point);
            if (point.equals(20)) {
                Reward reward = Reward.builder()
                        .type(RewardType.SMALL_REWARD)
                        .status(false)
                        .customer(customer)
                        .build();
                rewardService.addReward(reward);
            } else if (point.equals(40)) {
                Reward reward = Reward.builder()
                        .type(RewardType.BIG_REWARD)
                        .status(false)
                        .customer(customer)
                        .build();
                rewardService.addReward(reward);
            }
        }
    }
}
