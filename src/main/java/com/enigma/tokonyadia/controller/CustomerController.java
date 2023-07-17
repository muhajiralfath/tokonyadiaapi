package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.model.response.CommonResponse;
import com.enigma.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/customers")
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<Customer>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new customer")
                        .data(customerService.create(customer))
                        .build());
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<?> getAllCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(name = "email", required = false) String email
    ) {
        List<Customer> customers = customerService.searchByNameOrPhoneOrEmail(name, mobilePhone, email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all customer")
                        .data(customers)
                        .build());
    }

    @GetMapping(path = "/customers{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<Customer>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get customer by id")
                        .data(customerService.getById(id))
                        .build());
    }

    @PutMapping(path = "/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<Customer>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update customer")
                        .data(customerService.update(customer))
                        .build());
    }

    @DeleteMapping(path = "/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete customer")
                        .build());
    }

}
