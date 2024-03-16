package com.shopsmart.productservice.controller;

import com.shopsmart.productservice.dto.ProductRequest;
import com.shopsmart.productservice.dto.ProductResponse;
import com.shopsmart.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse product = productService.addProduct(productRequest);

        if (product.getName() == null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable String name) {
        return productService.getProduct(name);
    }

    @DeleteMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse deleteProduct(@PathVariable String name) {
        return productService.deleteProduct(name);
    }
}
