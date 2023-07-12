package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Product;
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
    public Product createNewProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @PostMapping(path = "/products/bulk")
    public List<Product> createBulkProduct(@RequestBody List<Product> products) {
        return productService.createBulk(products);
    }

    @GetMapping(path = "/products")
    public List<Product> getAllProduct(@RequestParam(name = "name", required = false) String name) {
        if (name != null) {
           return productService.getAllByName(name);
        }

        return productService.getAll();
    }


    // Put Mapping dengan Path Variable
//    @PutMapping(path = "/products/{id}")
//    public Product updateProductWithParam(
//            @RequestBody Product product,
//            @PathVariable(name = "id") String id
//    ) {
//        // TODO: 1. Cari Index untuk dirubah ke Product -> List
//        Optional<Product> first = products.stream().filter(p -> p.getId().equals(id)).findFirst();
//
//        if (first.isPresent()) {
//            int index = products.indexOf(first.get());
//            // TODO: 2. Jika sudah ditemukan kirimkan index dan product dari client
//            product.setId(id);
//            products.set(index, product);
//            return product;
//        }
//
//        // TODO: 3. Jika tidak ada return null
//        return null;
//    }

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
