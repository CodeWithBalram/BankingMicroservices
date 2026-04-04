package com.ultimate.payment.controller;

import com.ultimate.payment.service.PaymentService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private final PaymentService service;

    public WebhookController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> handle(@RequestBody String payload) {
        try {
            JSONObject json = new JSONObject(payload);

            String orderId = json.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity")
                    .getString("order_id");

            String paymentId = json.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity")
                    .getString("id");

            service.handleSuccess(orderId, paymentId);

            return ResponseEntity.ok("Payment updated successfully");

        } catch (NoSuchElementException e) {
            // Order not found in DB
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // logs the actual error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing webhook: " + e.getMessage());
        }
    }
}