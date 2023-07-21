package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    public void testCreateCustomerEmailConflict() {
        Customer customerToCreate = new Customer(); // Set up your Customer object for testing

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(ResponseStatusException.class, () -> customerService.create(customerToCreate));
    }

    @Test
    public void testGetCustomerById() {
        String customerId = "your-customer-id"; // Set the customer ID for testing
        Customer customerToReturn = new Customer(); // Set up the Customer object you expect to return

        Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(customerToReturn));

        Customer actual = customerService.getById(customerId);

        Assertions.assertEquals(customerToReturn, actual);
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        String customerId = "non-existing-id"; // Set a non-existing customer ID for testing

        Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> customerService.getById(customerId));
    }

    @Test
    public void testUpdateNonExistingCustomer() {
        String customerId = "non-existing-id"; // Set a non-existing customer ID for testing
        Customer customerToUpdate = new Customer(); // Set up your Customer object for testing
        customerToUpdate.setId(customerId);

        Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> customerService.update(customerToUpdate));
    }
    @Test
    public void testSearchCustomersNotFound() {
        String name = "non-existing-name"; // Set a non-existing name for testing
        String mobilePhone = "non-existing-phone"; // Set a non-existing mobile phone for testing
        String email = "non-existing-email"; // Set a non-existing email for testing

        Mockito.when(customerRepository.findAll((Sort) Mockito.any())).thenReturn(new ArrayList<>());

        List<Customer> searchedCustomers = customerService.searchByNameOrPhoneOrEmail(name, mobilePhone, email);

        Assertions.assertTrue(searchedCustomers.isEmpty());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> mockCustomers = new ArrayList<>(); // Set up a list of mock Customer objects for testing
        // Add some mock Customer objects to the list

        Mockito.when(customerRepository.findAll()).thenReturn(mockCustomers);

        List<Customer> actual = customerService.getAll();

        Assertions.assertEquals(mockCustomers, actual);
    }

    @Test
    void testDeleteById() {
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
    @Test
    public void testDeleteNonExistingCustomer() {
        String customerId = "non-existing-id"; // Set a non-existing customer ID for testing

        Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> customerService.deleteById(customerId));

        // Alternatively, you can also verify that the delete method was not called
        Mockito.verify(customerRepository, Mockito.never()).delete(Mockito.any());
    }
}