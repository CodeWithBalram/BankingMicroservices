package com.ultimate.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;import jakarta.validation.constraints.PositiveOrZero;import lombok.Data;

@Data
public class LoansDto
{    @NotEmpty(message = "mobile number cannot be null or empty")
     @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digit")
    private String mobileNumber;
    @NotEmpty(message="LaonNumber cannot be null or empty ")
    @Pattern(regexp="(^$|[0-9]{12})",message = "Loans number must be 1 digit")
    private String loanNumber;
    @NotEmpty(message="LoanType must not be empty")
    private String loanType;
    @Positive(message = "totalLoans must positive")

    private int totalLoan;
    @PositiveOrZero(message="amountPaid must be zero or positive")
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")

    private int outstandingAmount;
}
