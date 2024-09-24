package com.enigma.maju_mundur_eshop.service.impl;

import com.enigma.maju_mundur_eshop.constant.TransactionStatus;
import com.enigma.maju_mundur_eshop.dto.request.payment.PaymentCustomerDetailRequest;
import com.enigma.maju_mundur_eshop.dto.request.payment.PaymentItemDetailRequest;
import com.enigma.maju_mundur_eshop.dto.request.payment.PaymentRequest;
import com.enigma.maju_mundur_eshop.dto.request.payment.PaymentTransactionDetailRequest;
import com.enigma.maju_mundur_eshop.entity.Payment;
import com.enigma.maju_mundur_eshop.entity.Transaction;
import com.enigma.maju_mundur_eshop.entity.TransactionDetail;
import com.enigma.maju_mundur_eshop.repository.PaymentRepository;
import com.enigma.maju_mundur_eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestClient restClient;

    private final String SECRET_KEY;
    private final String BASE_URL;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              RestClient restClient,
                              @Value("${midtrans.api.key}") String SECRET_KEY,
                              @Value("${midtrans.api.snap-url}") String BASE_URL) {
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        this.SECRET_KEY = SECRET_KEY;
        this.BASE_URL = BASE_URL;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment createPayment(Transaction transaction) {
        long amount = transaction.getTransactionDetails().stream()
                .mapToLong(TransactionDetail::getPrice)
                .reduce(0L, Long::sum);

        List<PaymentItemDetailRequest> itemDetailRequests = transaction.getTransactionDetails()
                .stream()
                .map(detail -> {
                    return PaymentItemDetailRequest.builder()
                            .name(detail.getProduct().getProductName())
                            .price(detail.getPrice())
                            .quantity(detail.getQuantity())
                            .build();
                })
                .toList();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .transactionDetailRequest(PaymentTransactionDetailRequest.builder()
                        .orderId(transaction.getId())
                        .grossAmount(amount)
                        .build())
                .itemDetails(itemDetailRequests)
                .customerDetails(PaymentCustomerDetailRequest.builder()
                        .firstName(transaction.getCustomer() != null ? transaction.getCustomer().getName() : "Guest")
                        .phone(transaction.getCustomer() != null ? transaction.getCustomer().getPhoneNumber() : "Empty")
                        .build())
                .enabledPayments(List.of("shopeepay", "gopay", "bca_va", "bni_va", "bri_va"))
                .build();

        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(BASE_URL)
                .body(paymentRequest)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + SECRET_KEY)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, String>>() {});

        Map<String, String> body = response.getBody();
        if (body == null) {
            return null;
        }

        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus(TransactionStatus.pending)
                .build();
        paymentRepository.saveAndFlush(payment);

        return payment;
    }
}
