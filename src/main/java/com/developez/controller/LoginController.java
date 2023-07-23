package com.developez.controller;

import com.developez.model.Authority;
import com.developez.model.Customer;
import com.developez.repository.AuthorityRepository;
import com.developez.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class LoginController {

    private final CustomerRepository customerRepository;


    @Autowired
    public LoginController( CustomerRepository customerRepository,
                            AuthorityRepository authorityRepository ) {
        this.customerRepository = customerRepository;
    }



    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin( Authentication authentication ) {
        // Recupera l'utente autenticato in base all'email
        List<Customer> customers = customerRepository.findByEmail(authentication.getName());

        // Verifica se sono presenti utenti
        if (customers.size() > 0) {
            // Restituisci il primo utente trovato
            return customers.get(0);
        } else {
            // Se non sono presenti utenti, restituisci null
            return null;
        }
    }
}
