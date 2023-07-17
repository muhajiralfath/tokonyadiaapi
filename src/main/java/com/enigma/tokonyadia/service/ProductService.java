package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.model.request.ProductRequest;
import com.enigma.tokonyadia.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    List<ProductResponse> createBulk(List<ProductRequest> products);
    ProductResponse getById(String id);
    List<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice);
    ProductResponse update(ProductRequest product);
    void deleteById(String id);

}
