package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.ProductPrice;
import com.enigma.tokonyadia.repository.ProductPriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductPriceServiceImplTest {
    @Mock
    private ProductPriceRepository productPriceRepository;
    //    @InjectMocks
    private ProductPriceServiceImpl productPriceService;

    @BeforeEach
    void setUp() {
        productPriceService = new ProductPriceServiceImpl(productPriceRepository);
//        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProductPrice() {
        ProductPrice productPrice = new ProductPrice();

        when(productPriceRepository.save(any())).thenReturn(productPrice);

        ProductPrice savedProductPrice = productPriceService.create(productPrice);

        Assertions.assertEquals(productPrice, savedProductPrice);
        verify(productPriceRepository, times(1)).save(any());
    }

    @Test
    public void testGetProductPriceByIdFound() {
        String productId = "1";
        ProductPrice productPrice = new ProductPrice();

        when(productPriceRepository.findById(productId)).thenReturn(Optional.of(productPrice));

        ProductPrice foundProductPrice = productPriceService.getById(productId);

        Assertions.assertEquals(productPrice, foundProductPrice);
    }

    @Test
    public void testGetProductPriceByIdNotFound() {
        String productId = "1";

        when(productPriceRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productPriceService.getById(productId));
    }

    @Test
    public void testFindProductPriceActiveFound() {
        String productId = "1";
        Boolean active = true;
        ProductPrice productPrice = new ProductPrice();

        when(productPriceRepository.findByProduct_IdAndIsActive(productId, active)).thenReturn(Optional.of(productPrice));

        ProductPrice foundProductPrice = productPriceService.findProductPriceActive(productId, active);

        assertEquals(productPrice, foundProductPrice);
    }

    @Test
    public void testFindProductPriceActiveNotFound() {
        String productId = "1";
        Boolean active = true;

        when(productPriceRepository.findByProduct_IdAndIsActive(productId, active)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productPriceService.findProductPriceActive(productId, active));
    }
}