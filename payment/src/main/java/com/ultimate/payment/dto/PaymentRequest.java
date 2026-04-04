package com.ultimate.payment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Double amount;
    private String currency;
    private String userId;
    private String referenceType;
    private String referenceId;
}
