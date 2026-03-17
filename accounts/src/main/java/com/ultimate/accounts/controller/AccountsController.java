package com.ultimate.accounts.controller;

import com.ultimate.accounts.constants.AccountsConstants;
import com.ultimate.accounts.dto.CustomerDto;
import com.ultimate.accounts.dto.ResponseDto;
import com.ultimate.accounts.service.IAccountsService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController
{
    private IAccountsService iAccountsService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto)
    {
        iAccountsService.createAccounts(customerDto);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.STATUS_201));
    }
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})" ,message="Mobile number should be 10 didgit") String mobileNumber)
    {
       CustomerDto customerDto= iAccountsService.fetchAccount(mobileNumber);
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(customerDto);


    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> fetchAccount( @Valid @RequestBody CustomerDto customerDto)
    {
       boolean isUpdated=iAccountsService.updateAccount(customerDto);
       if(isUpdated)
       {
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_417_UPDATE));
       }
       else
       {
           return  ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_501));

       }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto>deleteAccount(@RequestParam  @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile no should be 10 digit") String mobileNumber)
    {
        boolean isDeleted=iAccountsService.deleteAccount( mobileNumber);

        if(isDeleted)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_501));
        }


    }
}
