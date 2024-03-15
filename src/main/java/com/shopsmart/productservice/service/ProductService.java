package com.shopsmart.productservice.service;

import com.shopsmart.productservice.dto.ProductRequest;
import com.shopsmart.productservice.dto.ProductResponse;
import com.shopsmart.productservice.exception.ProductAlreadyExistException;
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

    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        try {
            findProduct(product.getName());
            log.info("{} already exist", product.getName(), new ProductAlreadyExistException(
                    String.format("%s already exist", product.getName()))
            );

        } catch (ProductNotFoundException e) {
            log.info("{} does not exist", product.getName());
            productRepository.save(product);
            log.info("{} is saved", product.getName());
        }

        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Fetching product list");
        List<Product> productList = productRepository.findAll();

        if (!productList.isEmpty()) {
            log.info("product list found");
            return productList.stream()
                    .map(this::mapToProductResponse)
                    .toList();

        } else {
            log.info("Product list not found", new ProductNotFoundException("Product list not found"));
            return List.of();
        }
    }

    public ProductResponse getProduct(String name) {
        ProductResponse response = new ProductResponse();

        try {
            response = mapToProductResponse(findProduct(name));
            log.info("{} found", name);

        } catch (ProductNotFoundException e) {
            log.info("{} not found", name, e);
        }
        return response;
    }

    public ProductResponse deleteProduct(String name) {
        ProductResponse response = new ProductResponse();

        try {
            Product product = findProduct(name);
            log.info("{} found", name);
            productRepository.delete(product);
            log.info("{} deleted", name);
            response = mapToProductResponse(product);

        } catch (ProductNotFoundException e) {
            log.info("{} not found", name, e);
        }
        return response;
    }

    private Product findProduct(String name) {
        log.info("Fetching {}", name);
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
