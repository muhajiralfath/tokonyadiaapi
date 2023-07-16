package com.enigma.tokonyadia.repository;

import com.enigma.tokonyadia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    // Query Method
    List<Product> findAllByNameLikeIgnoreCase(String name);

    // Query Method Relation
    // Cari Semua Product dengan Product Price Lebih dari parameter
    List<Product> findAllByNameContainsOrProductPrices_PriceGreaterThan(String name, Long price);


}
