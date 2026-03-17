package com.ultimate.accounts.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto
{
    @NotEmpty(message = "name should not be null or empty")
    @Size(min=5,max=30, message="length of name should between 5 to 30")
    private String name;

    @NotEmpty(message="email should not be null or empty")
    @Email(message = "email should be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "mobileNumber should be 10 digit")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
