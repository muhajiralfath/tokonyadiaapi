package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.entity.ProductPrice;
import com.enigma.tokonyadia.entity.Store;
import com.enigma.tokonyadia.model.request.ProductRequest;
import com.enigma.tokonyadia.model.response.ProductResponse;
import com.enigma.tokonyadia.repository.ProductRepository;
import com.enigma.tokonyadia.service.ProductPriceService;
import com.enigma.tokonyadia.service.ProductService;
import com.enigma.tokonyadia.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductPriceService productPriceService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse create(ProductRequest request) {
        // TODO: Validasi Store
        Store store = storeService.getById(request.getStoreId());

        // TODO: Create Product Price
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(request.getPrice());
        productPrice.setStock(request.getStock());
        productPrice.setStore(store);
        ProductPrice savedProductPrice = productPriceService.create(productPrice);

        // TODO: Create Product
        Product product = new Product();
        product.setName(request.getProductName());
        product.setDescription(request.getDescription());
        Product savedProduct = productRepository.save(product);

        savedProductPrice.setProduct(savedProduct);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProductPrice.getPrice(),
                savedProductPrice.getStock(),
                store.getId()
        );
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
