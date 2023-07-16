package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.ProductPrice;
import com.enigma.tokonyadia.repository.ProductPriceRepository;
import com.enigma.tokonyadia.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;

    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }
}
