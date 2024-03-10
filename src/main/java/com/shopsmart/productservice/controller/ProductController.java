package com.shopsmart.productservice.controller;

import com.shopsmart.productservice.dto.ProductRequest;
import com.shopsmart.productservice.dto.ProductResponse;
import com.shopsmart.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
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
    public void deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
    }
}
