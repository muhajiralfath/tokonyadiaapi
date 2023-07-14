package com.enigma.tokonyadia.repository;

import com.enigma.tokonyadia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
