package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.entity.ProductPrice;
import com.enigma.tokonyadia.entity.Store;
import com.enigma.tokonyadia.model.request.ProductRequest;
import com.enigma.tokonyadia.model.response.ProductResponse;
import com.enigma.tokonyadia.model.response.StoreResponse;
import com.enigma.tokonyadia.repository.ProductRepository;
import com.enigma.tokonyadia.service.ProductPriceService;
import com.enigma.tokonyadia.service.ProductService;
import com.enigma.tokonyadia.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // TODO: Create Product
        Product product = new Product();
        product.setName(request.getProductName());
        product.setDescription(request.getDescription());
        productRepository.saveAndFlush(product);

        // TODO: Create Product Price
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(request.getPrice());
        productPrice.setStock(request.getStock());
        productPrice.setStore(store);
        productPrice.setProduct(product);
        productPrice.setIsActive(true);
        productPriceService.create(productPrice);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                productPrice.getPrice(),
                productPrice.getStock(),
                new StoreResponse(
                        store.getId(),
                        store.getName(),
                        store.getAddress()
                )
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
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream().map(product -> {
            Optional<ProductPrice> productPrice = product.getProductPrices().stream().filter(ProductPrice::getIsActive).findFirst();

            if (productPrice.isPresent()) {
                return new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        productPrice.get().getPrice(),
                        productPrice.get().getStock(),
                        new StoreResponse(
                                productPrice.get().getStore().getId(),
                                productPrice.get().getStore().getName(),
                                productPrice.get().getStore().getAddress()
                        )
                );
            }

            return null;
        }).collect(Collectors.toList());

        return productResponses;
    }

    @Override
    public List<Product> getAllByNameOrPrice(String name, Long price) {
        return productRepository.findAllByNameContainsOrProductPrices_PriceGreaterThan(name, price);
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
