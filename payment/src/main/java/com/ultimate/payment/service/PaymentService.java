package com.ultimate.payment.service;

import com.razorpay.Order;
import com.ultimate.payment.dto.PaymentRequest;
import com.ultimate.payment.dto.PaymentResponse;
import com.ultimate.payment.entity.Payment;
import com.ultimate.payment.event.PaymentEvent;
import com.ultimate.payment.exception.PaymentNotFoundException;
import com.ultimate.payment.integration.RazorpayService;
import com.ultimate.payment.kafka.PaymentEventPublisher;
import com.ultimate.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RazorpayService razorpayService;
    private final PaymentRepository repository;
    private final PaymentEventPublisher publisher;

    public PaymentService(RazorpayService razorpayService,
                          PaymentRepository repository,
                          PaymentEventPublisher publisher) {
        this.razorpayService = razorpayService;
        this.repository = repository;
        this.publisher = publisher;
    }

    public PaymentResponse createPayment(PaymentRequest request) throws Exception {

        Order order = razorpayService.createOrder(
                request.getAmount(),
                request.getCurrency()
        );

        Payment payment = Payment.builder()
                .orderId(order.get("id"))
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("CREATED")
                .userId(request.getUserId())
                .referenceType(request.getReferenceType())
                .referenceId(request.getReferenceId())
                .build();

        repository.save(payment);

        return PaymentResponse.builder()
                .orderId(order.get("id"))
                .status("CREATED")
                .build();
    }

    public void handleSuccess(String orderId, String paymentId) {

        Payment payment = repository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for orderId: " + orderId));
        if ("SUCCESS".equals(payment.getStatus())) return; // idempotent

        payment.setStatus("SUCCESS");
        payment.setPaymentId(paymentId);

        repository.save(payment);

        PaymentEvent event = PaymentEvent.builder()
                .orderId(orderId)
                .status("SUCCESS")
                .amount(payment.getAmount())
                .userId(payment.getUserId())
                .referenceType(payment.getReferenceType())
                .referenceId(payment.getReferenceId())
                .build();

        publisher.publish(event);
    }
}
