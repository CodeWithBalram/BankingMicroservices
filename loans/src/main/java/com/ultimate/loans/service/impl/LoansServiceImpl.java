package com.ultimate.loans.service.impl;

import com.ultimate.cards.exception.ResourceNotFoundException;import com.ultimate.loans.constants.LoansConstants;
import com.ultimate.loans.dto.LoansDto;import com.ultimate.loans.entity.Loans;
import com.ultimate.loans.exception.LoanAlreadyExistsException;
import com.ultimate.loans.mapper.LoansMapper;import com.ultimate.loans.repository.LoansRepository;
import com.ultimate.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoanService
{
    private LoansRepository loansRepository;
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }


    @Override
    public LoansDto fetchLoans(String mobileNumber)
    {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(

                ()->new ResourceNotFoundException("Loans","mobileNumber",mobileNumber)
        );


        return LoansMapper.mapToLoansDto(loans);
    }

    public boolean updateLoans(LoansDto loansDto)
    {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto);
        loansRepository.save(loans);
        return  true;
    }

    public boolean deleteLoans(String mobileNumber)
    {
       Loans loans= loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
               ()-> new ResourceNotFoundException("Loans","mobileNumber",mobileNumber)
       );
       loansRepository.deleteById(loans.getLoanId());
       return true;
    }
}
