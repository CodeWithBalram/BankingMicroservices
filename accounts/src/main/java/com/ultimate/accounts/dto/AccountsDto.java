package com.ultimate.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto
{
    @NotEmpty(message = "accountNumber should not be empaty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "account number should be 10 digit")
    private Long accountNumber;

    @NotEmpty(message = "accountType should not be empaty")
    private String accountType;

    @NotEmpty(message ="branchAddress should not empaty")
    private String branchAddress;
}
