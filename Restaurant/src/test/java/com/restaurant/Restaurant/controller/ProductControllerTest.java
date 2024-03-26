package com.restaurant.Restaurant.controller;

import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.models.dto.ProductDTO;
import com.restaurant.Restaurant.service.products.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    private ProductDTO productDTO;
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;


    @BeforeEach
    public void setUp(){
        productDTO = ProductDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .fantasyName("Hamburger With Chess")
                .category(ProductEntity.Category.MEATS)
                .description("Hamburger with chess extra")
                .price(2000D)
                .available(true)
                .build();
    }

    @Test
    void shouldReturnResponseGetProductByUuid(){
        Mockito.when(productService.getProductByUuid(productDTO.getUuid())).thenReturn(productDTO);
        var response= productController.getProductByUuid(productDTO.getUuid());
        verify(productService,times(1)).getProductByUuid(productDTO.getUuid());
        assertEquals(ResponseEntity.ok(productDTO),response);
    }
    @Test
    void shouldReturnResponseCreateProduct(){
        Mockito.when(productService.createProduct(productDTO)).thenReturn(productDTO);
        var response= productController.createProduct(productDTO);
        verify(productService,times(1)).createProduct(productDTO);
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(productDTO),response);
    }
    @Test
    void shouldReturnResponseUpdateProduct(){
        var response= productController.updateProduct(productDTO.getUuid(),productDTO);
        verify(productService,times(1)).updateProduct(productDTO);
        assertEquals(ResponseEntity.status(204).body(""),response);
    }
    @Test
    void shouldReturnResponseDeleteProduct(){
        var response= productController.deleteProduct(productDTO.getUuid());
        verify(productService,times(1)).deleteProduct(productDTO.getUuid());
        assertEquals(ResponseEntity.status(204).body(""),response);
    }
}