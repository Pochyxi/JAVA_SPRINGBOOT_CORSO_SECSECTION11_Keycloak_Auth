package com.developez.controller;

import com.developez.model.Customer;
import com.developez.model.Loans;
import com.developez.repository.CustomerRepository;
import com.developez.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    private final LoansRepository loanRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LoansController(LoansRepository loansRepository,
                           CustomerRepository customerRepository ) {
        this.loanRepository = loansRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myLoans")
    @PreAuthorize( "hasAnyRole('ADMIN', 'USER')" )
    public List<Loans> getLoanDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customers.get(0).getId());
            if (loans != null ) {
                return loans;
            }
        }
        return null;
    }

}
