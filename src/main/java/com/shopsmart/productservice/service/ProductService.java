package com.shopsmart.productservice.service;

import com.shopsmart.productservice.dto.ProductRequest;
import com.shopsmart.productservice.dto.ProductResponse;
import com.shopsmart.productservice.exception.ProductNotFoundException;
import com.shopsmart.productservice.model.Product;
import com.shopsmart.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    // FIXME: This should have its own service for store owner to add products.
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        try {
            Product existingProduct = findProduct(product.getName());
            log.info("This product already exist");
            return new ProductResponse();
        } catch (ProductNotFoundException e) {
            log.info("Product does not exist", e);
        }

        productRepository.save(product);
        log.info("{} is saved", product.getName());
        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        if (!productList.isEmpty()) {
            return productList.stream()
                    .map(this::mapToProductResponse)
                    .toList();

        } else {
            log.info("Product list not found", new ProductNotFoundException("Product list not found"));
            return List.of();
        }
    }

    public ProductResponse getProduct(String name) {
        try {
            return mapToProductResponse(findProduct(name));

        } catch (ProductNotFoundException e) {
            log.info("Product not found", e);
        }
        return new ProductResponse();
    }

    // FIXME: This should have its own service for store owner to add products.
    public ProductResponse deleteProduct(String name) {
        try {
            Product product = findProduct(name);
            productRepository.delete(findProduct(name));
            return mapToProductResponse(product);

        } catch (ProductNotFoundException e) {
            log.info("Product not found", e);
        }
        return new ProductResponse();
    }

    private Product findProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() ->
                        new ProductNotFoundException(String.format("%s not found", name))
                );
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
