package com.ultimate.loans.service;

import com.ultimate.loans.dto.LoansDto;
public interface ILoanService
{
    void createLoan(String mobileNumber);

    LoansDto fetchLoans(String mobileNumber);

    boolean updateLoans( LoansDto loansDto);

    boolean deleteLoans(String mobileNumber);
}
