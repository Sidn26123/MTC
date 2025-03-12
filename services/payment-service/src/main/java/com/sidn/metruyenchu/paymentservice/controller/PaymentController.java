package com.sidn.metruyenchu.paymentservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
//    PaymentService paymentService;
//
//    @PostMapping
//    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
//        log.info("Creating payment for order: {}", paymentRequest.getOrderId());
//        return paymentService.createPayment(paymentRequest);
//    }
}
