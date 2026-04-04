package com.ultimate.payment.controller;


import com.ultimate.payment.dto.PaymentRequest;
import com.ultimate.payment.dto.PaymentResponse;
import com.ultimate.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public PaymentResponse create(@RequestBody PaymentRequest request) throws Exception {
        PaymentResponse response = service.createPayment(request);

        // Print orderId to console
        System.out.println("Created orderId: " + response.getOrderId());

        return response;
    }
}
