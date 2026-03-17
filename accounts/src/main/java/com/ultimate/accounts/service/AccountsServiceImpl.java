package com.ultimate.accounts.service;

import com.ultimate.accounts.constants.AccountsConstants;
import com.ultimate.accounts.dto.AccountsDto;
import com.ultimate.accounts.dto.CustomerDto;
import com.ultimate.accounts.entity.Accounts;
import com.ultimate.accounts.entity.Customer;
import com.ultimate.accounts.exception.CustomerAlreadyExistsException;
import com.ultimate.accounts.exception.ResourceNotFoundException;
import com.ultimate.accounts.mapper.AccountsMapper;
import com.ultimate.accounts.mapper.CustomerMapper;
import com.ultimate.accounts.repository.AccountsRepository;
import com.ultimate.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService
{
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccounts(CustomerDto customerDto)
    {

        Optional<Customer> optionalCustomer =customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent())
        {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber");
        }
        Customer customer=CustomerMapper.mapToCustomer(customerDto,new Customer());

        Customer savedCustomer=customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber)
    {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Accounts","customerId",customer.getCustomerId().toString())
        );

       CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto)
    {  boolean isUpdated=false;
        AccountsDto accountsDto=customerDto.getAccountsDto();
        if(accountsDto!=null)
        {
           Accounts accounts= accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                ()->new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
           );

           AccountsMapper.mapToAccounts(accountsDto,accounts);
           accounts=accountsRepository.save(accounts);

            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(

                ()->new ResourceNotFoundException("customer","CustomerId",customerId.toString())
            );

                CustomerMapper.mapToCustomer(customerDto,customer);
                customer=customerRepository.save(customer);

                isUpdated=true;

        }


        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber)
    {

        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return false;

    }


}
