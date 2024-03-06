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

    public void addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("{} is saved", product.getName());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        if (!productList.isEmpty()) {
            return productList.stream()
                    .map(this::mapToProductResponse)
                    .toList();
        } else {
            throw new ProductNotFoundException("Product list not found");
        }
    }

    public ProductResponse getProduct(String name) {
         Product product = productRepository.findByName(name)
                 .orElseThrow(() ->
                         new ProductNotFoundException(String.format("%s not found", name)));

        return mapToProductResponse(product);
    }

    public void deleteProduct(String name) {
        if (getProduct(name) != null) {
            productRepository.deleteByName(name);

            log.info("{} deleted", name);
        } else {
            throw new ProductNotFoundException(String.format("%s not found", name));
        }
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
