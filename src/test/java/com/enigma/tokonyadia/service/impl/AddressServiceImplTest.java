package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Address;
import com.enigma.tokonyadia.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAddress() {
        Address addressToCreate = new Address(); // Set up your Address object for testing

        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(addressToCreate);

        Address actual = addressService.create(addressToCreate);

        Assertions.assertEquals(addressToCreate, actual);
    }
}