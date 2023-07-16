package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/customers")
    public Customer createNewCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(name = "email", required = false) String email
    ) {
        return customerService.searchByNameOrPhoneOrEmail(name, mobilePhone, email);
    }

    @GetMapping(path = "/customers{id}")
    public Customer getById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping(path = "/customers/{id}")
    public String deleteCustomer(@PathVariable String id) {
        return customerService.deleteById(id);
    }

}
