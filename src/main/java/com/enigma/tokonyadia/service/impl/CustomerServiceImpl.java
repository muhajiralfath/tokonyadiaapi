package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Address;
import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.service.AddressService;
import com.enigma.tokonyadia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
    }

//    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public Customer create(Customer customer) {
//        Address address = addressService.create(customer.getAddress());
//        customer.setAddress(address);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> createBulk(List<Customer> customers) {
        return customerRepository.saveAll(customers);
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchByNameOrPhoneOrEmail(String name, String phone, String email) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        Customer currentCustomer = getById(customer.getId());

        if (currentCustomer != null) {
            return customerRepository.save(customer);
        }

        return null;
    }

    @Override
    public String deleteById(String id) {
//        Customer customer = getById(id);

        boolean exists = customerRepository.existsById(id);

        if (exists) {
            customerRepository.deleteById(id);
            return "Success";
        }

//        if (customer != null) {
//            customerRepository.delete(customer);
//        }

        return "Failed";
    }
}
