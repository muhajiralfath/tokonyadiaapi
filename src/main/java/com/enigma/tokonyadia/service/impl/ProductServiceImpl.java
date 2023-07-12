package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> products;

    public ProductServiceImpl() {
        this.products = new ArrayList<>();
    }

    @Override
    public Product create(Product product) {
        product.setId(UUID.randomUUID().toString());
        products.add(product);
        return product;
    }

    @Override
    public List<Product> createBulk(List<Product> products) {
        for (Product product : products) {
            product.setId(UUID.randomUUID().toString());
            this.products.add(product);
        }

        return products;
    }

    @Override
    public Product getById(String id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public List<Product> getAllByName(String name) {
        List<Product> filteredProduct = products.stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());

        return filteredProduct;
    }

    @Override
    public Product update(Product product) {
        // TODO: 1. Cari Index untuk dirubah ke Product -> List
        Optional<Product> first = products.stream().filter(p -> p.getId().equals(product.getId())).findFirst();

        if (first.isPresent()) {
            int index = products.indexOf(first.get());
            // TODO: 2. Jika sudah ditemukan kirimkan index dan product dari client
            products.set(index, product);
            return product;
        }

        // TODO: 3. Jika tidak ada return null
        return null;
    }

    @Override
    public String deleteById(String id) {
        Optional<Product> productOptional = products.stream().filter(product -> product.getId().equals(id)).findFirst();

        if (productOptional.isPresent()) {
            products.remove(productOptional.get());
            return "Sukses menghapus data product: " + productOptional.get().getId();
        }

        return "Product tidak ditemukan";
    }
}
