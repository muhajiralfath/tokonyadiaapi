package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.model.request.ProductRequest;
import com.enigma.tokonyadia.model.response.ProductResponse;
import com.enigma.tokonyadia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/products")
    public ProductResponse createNewProduct(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PostMapping(path = "/products/bulk")
    public List<Product> createBulkProduct(@RequestBody List<Product> products) {
        return productService.createBulk(products);
    }

    @GetMapping(path = "/products")
    public List<Product> getAllProduct(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "price", required = false) Long price
    ) {
        if (name != null || price != null) {
           return productService.getAllByNameOrPrice(name, price);
        }

        return productService.getAll();
    }

    //  Cara 2 Request Body
    @PutMapping(path = "/products")
    public Product updateProduct(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping(path = "/products/{id}")
    public String deleteById(@PathVariable(name = "id") String id) {
        return productService.deleteById(id);
    }

}
