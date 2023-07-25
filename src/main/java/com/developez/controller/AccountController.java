package com.developez.controller;

import com.developez.model.Accounts;
import com.developez.model.Authority;
import com.developez.model.Customer;
import com.developez.repository.AccountsRepository;
import com.developez.repository.AuthorityRepository;
import com.developez.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AccountController {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AccountController( AccountsRepository accountsRepository,
                              CustomerRepository customerRepository,
                              AuthorityRepository authorityRepository ) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("/myAccount")
    @PreAuthorize( "hasAnyRole('ADMIN', 'USER')" )
    public ResponseEntity<?> getAccountDetails(@RequestParam String email) {
//        String clientEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Accounts> accounts = accountsRepository.findAccountsByAccountEmail(email);

        if (accounts.isEmpty()) {
            return new ResponseEntity<>("Accounts not found", HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping ("/newAccount")
    public ResponseEntity<?> saveAccountDetails( @RequestBody Accounts accounts ) {

//        customerRepository.findById( accounts.getCustomerId() ).orElseThrow(
//                () -> new ResponseStatusException( HttpStatus.NOT_FOUND, "Customer not found" )
//        );

        accounts.setCreateDt( LocalDate.now()  );

        return new ResponseEntity<>( accountsRepository.save( accounts ), HttpStatus.OK );
    }

    @PostMapping ("/makeAdmin")
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> makeAdmin( @RequestParam int customerId ) {
        Customer customer = customerRepository.findById( customerId ).orElseThrow(
                () -> new ResponseStatusException( HttpStatus.NOT_FOUND, "Customer not found" )
        );

        Authority authority = new Authority();
        authority.setName( "ROLE_ADMIN" );
        authority.setCustomer( customer );
        Authority newAuthority = authorityRepository.save( authority );

        return new ResponseEntity<>( newAuthority, HttpStatus.OK );
    }
}
