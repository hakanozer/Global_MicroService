package com.works.services;

import com.works.entities.Customer;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final CustomerRepository customerRepository;
    final TinkEncDec tinkEncDec;
    final HttpServletRequest request;

    public ResponseEntity register(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(customer.getEmail());
        Map<String, Object> hm = new LinkedHashMap<>();
        if(optionalCustomer.isPresent()) {
            hm.put("status", false);
            hm.put("message", "Email Fail");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }else {
            customer.setPassword(tinkEncDec.encrypt(customer.getPassword()));
            customerRepository.save(customer);
            hm.put("status", true);
            hm.put("result", customer);
            return new ResponseEntity(hm, HttpStatus.OK);
        }
    }

    public ResponseEntity login( Customer customer ) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(customer.getEmail());
        Map<String, Object> hm = new LinkedHashMap<>();
        if ( optionalCustomer.isPresent() ) {
            Customer cus = optionalCustomer.get();
            String dbPass = tinkEncDec.decrypt(cus.getPassword());
            if ( dbPass.equals(customer.getPassword()) ) {
                hm.put("status", true);
                hm.put("result", cus);
                request.getSession().setAttribute("customer", cus);
                return new ResponseEntity(hm, HttpStatus.OK);
            }
        }
        hm.put("status", false);
        hm.put("message", "Email or Password Fail");
        return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
    }


}
