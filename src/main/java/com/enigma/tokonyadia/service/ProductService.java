package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.model.request.ProductRequest;
import com.enigma.tokonyadia.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    List<Product> createBulk(List<Product> products);
    Product getById(String id);
    List<Product> getAll();
    List<Product> getAllByName(String name);
    Product update(Product product);
    String deleteById(String id);

}
