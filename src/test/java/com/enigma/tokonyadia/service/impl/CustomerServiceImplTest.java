package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void shouldReturnCustomerWhenCreateNewCustomer() {
        // Given
        Customer edy = new Customer();
        edy.setName("edy");

        // When
        when(customerRepository.save(edy)).thenReturn(edy);
        Customer actual = customerService.create(edy);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("edy", actual.getName());
        verify(customerRepository, times(1)).save(edy);
    }

    @Test
    void getAll() {
    }

    @Test
    void deleteById() {
        // Given
        Customer edy = Customer.builder()
                .id("1")
                .name("edy")
                .mobilePhone("08123")
                .build();

        // When
        when(customerRepository.findById(edy.getId()))
                .thenReturn(Optional.of(edy));
        doNothing().when(customerRepository).delete(edy);
        customerService.deleteById(edy.getId());

        // Verify
        verify(customerRepository, times(1))
                .delete(edy);
    }
}