package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.model.request.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    Customer create(Customer customer);
    List<Customer> createBulk(List<Customer> customers);
    Customer getById(String id);
    List<Customer> getAll();
    List<Customer> searchByNameOrPhoneOrEmail(String name, String phone, String email);
    Customer update(CustomerRequest request);
    void deleteById(String id);

}
