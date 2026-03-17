package com.ultimate.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data

public class CardsDto {
    @NotEmpty(message = "mobile number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digit")
    private String mobileNumber;
    @NotEmpty(message = "Card number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "card number must be 12 digit")
    private String cardNumber;
    @NotEmpty(message = "card type not be null or empty")
    private String cardType;
    @Positive(message = "Total limit should be greater than zero")
    private int totalLimit;
    @PositiveOrZero(message = "Available amount should be positive or equal to zero")
    private int availableAmount;
    @PositiveOrZero(message = "Amount used greater than zero or equal to zero")
    private int amountUsed;
}
