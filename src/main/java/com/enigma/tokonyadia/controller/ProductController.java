package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.model.request.ProductRequest;
import com.enigma.tokonyadia.model.response.CommonResponse;
import com.enigma.tokonyadia.model.response.ProductResponse;
import com.enigma.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(path = "/products")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new product")
                        .data(productResponse)
                        .build());
    }

    @PostMapping(path = "/products/bulk")
    public ResponseEntity<?> createBulkProduct(@RequestBody List<ProductRequest> products) {
        List<ProductResponse> productResponses = productService.createBulk(products);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<List<ProductResponse>>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create bulk customer")
                        .data(productResponses)
                        .build());
    }

    @GetMapping(path = "/products")
    public ResponseEntity<?> getAllProduct(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice) {
        List<ProductResponse> productResponses = productService.getAllByNameOrPrice(name, maxPrice);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<ProductResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all customer")
                        .data(productResponses)
                        .build());
    }

    //  Cara 2 Request Body
    @PutMapping(path = "/products")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update customer")
                        .data(productResponse)
                        .build());
    }

    @DeleteMapping(path = "/products/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") String id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete customer")
                        .build());
    }

}
