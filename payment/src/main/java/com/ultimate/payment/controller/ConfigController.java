package com.ultimate.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${razorpay.key}")
    private String razorpayKey;

    @GetMapping("/razorpay")
    public String getRazorpayKey() {
        return razorpayKey; // only public key sent to frontend
    }
}