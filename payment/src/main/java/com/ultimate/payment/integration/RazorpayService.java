package com.ultimate.payment.integration;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RazorpayService {

    private final RazorpayClient client;

    public RazorpayService(RazorpayClient client) {
        this.client = client;
    }

    public Order createOrder(Double amount, String currency) throws Exception {

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", currency);
        options.put("receipt", "txn_" + System.currentTimeMillis());

        return client.orders.create(options);
    }
}
