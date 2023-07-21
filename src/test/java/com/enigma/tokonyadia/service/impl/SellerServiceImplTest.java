package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Seller;
import com.enigma.tokonyadia.entity.Store;
import com.enigma.tokonyadia.entity.UserCredential;
import com.enigma.tokonyadia.model.response.SellerResponse;
import com.enigma.tokonyadia.model.response.StoreResponse;
import com.enigma.tokonyadia.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @BeforeEach
    public void setUp() {
        sellerRepository = mock(SellerRepository.class);
        sellerService = new SellerServiceImpl(sellerRepository);
    }

    @Test
    public void createSeller_Success() {
        // Create a sample Seller object for testing
        Seller seller = new Seller();
        seller.setUsername("sellerUser");
        seller.setUserCredential(new UserCredential()); // Assuming there's a UserCredential associated with the seller

        // Mock the behavior of the sellerRepository.save() method
        when(sellerRepository.save(seller)).thenReturn(seller);

        // Call the create() method of the SellerService
        SellerResponse result = sellerService.create(seller);

        // Verify that the sellerRepository.save() method is called once
        verify(sellerRepository, times(1)).save(seller);

        // Verify the result
        assertNotNull(result);
        assertEquals("sellerUser", result.getUsername());
        assertEquals(seller.getUserCredential().getEmail(), result.getEmail());
        assertNotNull(result.getStore());
    }

    @Test
    public void createSeller_DuplicateUsername_ThrowsConflictException() {
        // Create a sample Seller object for testing
        Seller seller = new Seller();
        seller.setUsername("sellerUser");
        seller.setUserCredential(new UserCredential());

        // Mock the behavior of the sellerRepository.save() method
        when(sellerRepository.save(seller)).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        // Call the create() method of the SellerService and expect an exception
        assertThrows(ResponseStatusException.class, () -> sellerService.create(seller));

        // Verify that the sellerRepository.save() method is called once
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    public void getById_ExistingSeller_ReturnsSellerResponse() {
        // Create a sample Seller object for testing
        String sellerId = "seller123";
        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setUsername("sellerUser");
        seller.setUserCredential(new UserCredential());

        // Mock the behavior of the sellerRepository.findById() method
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));

        // Call the getById() method of the SellerService
        SellerResponse result = sellerService.getById(sellerId);

        // Verify that the sellerRepository.findById() method is called once
        verify(sellerRepository, times(1)).findById(sellerId);

        // Verify the result
        assertNotNull(result);
        assertEquals("sellerUser", result.getUsername());
        assertEquals(seller.getUserCredential().getEmail(), result.getEmail());
        assertNotNull(result.getStore());
    }

    @Test
    public void getById_NonExistingSeller_ThrowsNotFoundException() {
        // Create a sample Seller ID for testing
        String sellerId = "seller123";

        // Mock the behavior of the sellerRepository.findById() method
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        // Call the getById() method of the SellerService and expect an exception
        assertThrows(ResponseStatusException.class, () -> sellerService.getById(sellerId));

        // Verify that the sellerRepository.findById() method is called once
        verify(sellerRepository, times(1)).findById(sellerId);
    }

    // Similarly, you can write tests for getEmailByStoreId() method
    // Don't forget to mock the relevant methods in the sellerRepository as needed.
}
