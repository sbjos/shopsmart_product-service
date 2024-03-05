package com.shopsmart.productservice.service;

import com.shopsmart.productservice.dto.ProductRequest;
import com.shopsmart.productservice.dto.ProductResponse;
import com.shopsmart.productservice.model.Product;
import com.shopsmart.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProduct(String id) {
         Product product = Optional.of(productRepository.findById(id)).get()
                 .orElse(new Product());

        return mapToProductResponse(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);

        log.info("Product {} is deleted", id);
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
