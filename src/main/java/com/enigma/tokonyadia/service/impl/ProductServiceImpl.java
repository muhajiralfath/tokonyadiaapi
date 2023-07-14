package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.repository.ProductRepository;
import com.enigma.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> createBulk(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllByName(String name) {
        return null;
    }

    @Override
    public Product update(Product product) {
        Product currentProduct = getById(product.getId());

        if (currentProduct != null) {
            return productRepository.save(product);
        }

        return null;
    }

    @Override
    public String deleteById(String id) {
        Product currentProduct = getById(id);

        if (currentProduct != null) {
            productRepository.delete(currentProduct);
            return "Success";
        }

        return "Failed";
    }
}
