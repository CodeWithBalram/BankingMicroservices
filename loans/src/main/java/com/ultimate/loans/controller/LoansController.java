package com.ultimate.loans.controller;

import com.ultimate.loans.constants.LoansConstants;
import com.ultimate.loans.dto.LoansDto;import com.ultimate.loans.dto.ResponseDto;
import com.ultimate.loans.service.ILoanService;
import jakarta.validation.Valid;import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api",produces={MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController
{
    private ILoanService iLoanService;
   @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid @RequestBody String mobileNumber)
   {
       iLoanService.createLoan(mobileNumber);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
   }
  @GetMapping("/fetch")
   public ResponseEntity<LoansDto>fetchLoansDetails(@Valid @RequestParam String mobileNumber)
   {
      LoansDto loansDto= iLoanService.fetchLoans(mobileNumber);
      return  ResponseEntity
              .status(HttpStatus.OK)
              .body(loansDto);
   }
    @PutMapping("/update")
   public ResponseEntity<ResponseDto> updateLoansDetails(@RequestBody LoansDto loansDto)
   {
      boolean isUpdated=iLoanService.updateLoans(loansDto);

      if(isUpdated)
      {
          return  ResponseEntity
                  .status(HttpStatus.OK)
                  .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
      }
      else
      {
          return ResponseEntity
                  .status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));
      }
   }

   @DeleteMapping("/delete")
   public ResponseEntity<ResponseDto> deleteLoans(@RequestParam String mobileNumber)
   {
       boolean isDeleted=iLoanService.deleteLoans(mobileNumber);

       if(isDeleted)
       {
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
       }
       else
       {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE));
       }
   }



}
