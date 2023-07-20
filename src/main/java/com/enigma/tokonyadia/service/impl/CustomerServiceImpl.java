package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.model.request.CustomerRequest;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already used");
        }
    }

    @Override
    public List<Customer> createBulk(List<Customer> customers) {
        return customers.stream().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchByNameOrPhoneOrEmail(String name, String mobilePhone, String email) {
        Specification<Customer> specification = (root, query, criteriaBuilder) -> {
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

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        return customerRepository.findAll(specification);
    }

    @Override
    public Customer update(CustomerRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticationName = authentication.getName();

        Customer currentCustomer = getById(request.getId());

        if (currentCustomer != null) {
            if (authenticationName.equals(currentCustomer.getEmail())) {
                currentCustomer.setName(request.getName());
                currentCustomer.setMobilePhone(request.getMobilePhone());
                if(currentCustomer.getAddress() == null){
                    currentCustomer.setAddress(request.getAddress());
                } else {
                    currentCustomer.getAddress().setStreet(request.getAddress().getStreet());
                    currentCustomer.getAddress().setCity(request.getAddress().getCity());
                    currentCustomer.getAddress().setProvince(request.getAddress().getProvince());
                }
                return customerRepository.save(currentCustomer);
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have access to change the data!");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteById(String id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }
}
