package com.restaurant.Restaurant.controller;

import com.restaurant.Restaurant.models.dto.ProductDTO;
import com.restaurant.Restaurant.service.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<?> getProductByUuid (@PathVariable String uuid) {
        return ResponseEntity.ok(productService.getProductByUuid(uuid));
    }
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
            ProductDTO product=productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping(path = "/{uuid}")
    public ResponseEntity<?> updateProduct(@PathVariable String uuid, @Valid @RequestBody ProductDTO productDTO){
        productDTO.setUuid(uuid);
        productService.updateProduct(productDTO);
        return ResponseEntity.status(204).body("");
    }
    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<?> deleteProduct(@PathVariable String uuid){
        productService.deleteProduct(uuid);
        return ResponseEntity.status(204).body("");
    }





}
