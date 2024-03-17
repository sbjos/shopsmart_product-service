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
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.addProduct(productRequest);

        if (response.getName() == null)
            return new ResponseEntity<>("Product already exist", HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse> response = productService.getAllProducts();

        if (response.isEmpty())
            return new ResponseEntity<>("Product list not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<?> getProduct(@PathVariable String name) {
        ProductResponse response = productService.getProduct(name);

        if (response.getId() == null)
            return new ResponseEntity<>(String.format("%s not found", name), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<?> deleteProduct(@PathVariable String name) {
        ProductResponse response = productService.deleteProduct(name);

        if (response.getId() == null)
            return new ResponseEntity<>(String.format("%s does not exist", name), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
