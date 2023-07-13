package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {

    private final List<Customer> customers;

    public CustomerServiceImpl() {
        this.customers = new ArrayList<>();
    }

    @Override
    public Customer create(Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        customers.add(customer);
        return customer;
    }

    @Override
    public List<Customer> createBulk(List<Customer> customers) {
        for (Customer customer : customers) {
            customer.setId(UUID.randomUUID().toString());
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> customer = customers.stream().filter(c -> c.getId().equals(id)).findFirst();
        return customer.orElse(null);
    }

    @Override
    public List<Customer> getAll() {
        return customers;
    }

    @Override
    public List<Customer> searchByNameOrPhoneOrEmail(String name, String phone, String email) {
        return customers.stream()
                .filter(c -> c.getName().contains(name) || c.getMobilePhone().contains(phone) || c.getEmail().contains(email))
                .collect(Collectors.toList());
    }

    @Override
    public Customer update(Customer customer) {
        Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId().equals(customer.getId())).findFirst();

        if (customerOptional.isPresent()) {
            int index = customers.indexOf(customerOptional.get());
            customers.set(index, customer);
        }

        return null;
    }

    @Override
    public String deleteById(String id) {
        Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId().equals(id)).findFirst();

        if (customerOptional.isPresent()) {
            customers.remove(customerOptional.get());
            return "Sukses Menghapus Customer";
        }

        return "Gagal Menghapus Customer";
    }
}
