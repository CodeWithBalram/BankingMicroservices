package com.ultimate.payment.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentEvent {

    private String orderId;
    private String status;
    private Double amount;
    private String userId;
    private String referenceType;
    private String referenceId;
}
