package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.service.AddressService;
import com.enigma.tokonyadia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    public List<Customer> searchByNameOrPhoneOrEmail(String name, String mobilePhone, String email) {
        Specification<Customer> specification = (root, query, criteriaBuilder) -> {
            // Root -> Entity
            // Query -> Where
            // CriteriaBuilder -> Equal (=), NotEqual (<>), Like,

            // Predicate -> Logika yang digunakan untuk melakukan pemfilteran pada entitas/data
//            Predicate namePredicate2 = criteriaBuilder.equal(root.get("name"), name);
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
                predicates.add(namePredicate);
            }

            if (mobilePhone != null) {
                Predicate mobilePhonePredicate = criteriaBuilder.equal(root.get("mobilePhone"), mobilePhone);
                predicates.add(mobilePhonePredicate);
            }

            if (email != null) {
                Predicate emailPredicate = criteriaBuilder.equal(root.get("email"), email);
                predicates.add(emailPredicate);
            }

            // SELECT * FROM Customer WHERE name LIKE %name-parameter%
            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };

        return customerRepository.findAll(specification);
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
