package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.VNPayResponse;
import com.sidn.metruyenchu.paymentservice.service.PaymentService;
import com.sidn.metruyenchu.paymentservice.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
    WalletService walletService;
//    PaymentService paymentService;
//
//    @PostMapping
//    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
//        log.info("Creating payment for order: {}", paymentRequest.getOrderId());
//        return paymentService.createPayment(paymentRequest);
//    }



//    private final PaymentService paymentService;
//    @GetMapping("/vn-pay")
//    public ApiResponse<VNPayResponse> pay(HttpServletRequest request) {
////        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
//        return ApiResponse.<VNPayResponse>builder()
//                .result(paymentService.createVnPayPayment(request))
//                .build();
//    }
//    @GetMapping("/vn-pay-callback")
//    public ApiResponse<VNPayResponse> payCallbackHandler(HttpServletRequest request) {
//        log.info("PaymentController.payCallbackHandler");
//        String status = request.getParameter("vnp_ResponseCode");
//        walletService.processVnPayCallback(request);
//        if (status.equals("00")) {
////            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
//            return ApiResponse.<VNPayResponse>builder()
//                    .result(VNPayResponse.builder()
//                            .code("00")
//                            .message("Success")
//                            .paymentUrl("")
//                            .build())
//                    .build();
//        } else {
////            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
//            return ApiResponse.<VNPayResponse>builder()
//                    .result(VNPayResponse.builder()
//                            .code("99")
//                            .message("Failed")
//                            .paymentUrl("")
//                            .build())
//                    .build();
////            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
//        }
//    }



//    private final WalletService walletService;

    /**
     * Handle VNPAY payment return
     * @param request the HTTP request
     * @return redirect to appropriate page
     */
    @GetMapping("/vnpay/return")
    public ResponseEntity<Map<String, String>> vnpayReturn(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }

        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        Map<String, String> response = new HashMap<>();

        if ("00".equals(vnp_ResponseCode)) {
            response.put("status", "success");
            response.put("message", "Payment successful");
        } else {
            response.put("status", "failed");
            response.put("message", "Payment failed");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Handle VNPAY payment callback
     * @param request the HTTP request
     * @return processing status
     */
    @PostMapping("/vnpay/callback")
    public ResponseEntity<Map<String, String>> vnpayCallback(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }

        boolean processed = walletService.processVnPayCallback(params);
        Map<String, String> response = new HashMap<>();

        if (processed) {
            response.put("status", "success");
            response.put("message", "Callback processed successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "failed");
            response.put("message", "Failed to process callback");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }




}
