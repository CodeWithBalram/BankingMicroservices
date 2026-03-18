package com.ultimate.loans.controller;

import com.ultimate.loans.constants.LoansConstants;
import com.ultimate.loans.dto.ResponseDto;
import com.ultimate.loans.service.ILoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api",produces={MediaType.APPLICATION_JSON_VALUE})
public class LoansController
{
    private ILoanService iLoanService;
   @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestBody String mobileNumber)
   {
       iLoanService.createLoan(mobileNumber);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
   }
}
