package com.restaurant.Restaurant.service.products;

import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.exception.impl.FantasyNameExistsException;
import com.restaurant.Restaurant.exception.impl.ProductNotFoundException;
import com.restaurant.Restaurant.mapper.ProductMapper;
import com.restaurant.Restaurant.models.dto.ProductDTO;
import com.restaurant.Restaurant.repository.IProductRepositoryJPA;
import com.restaurant.Restaurant.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductDTO productDTO;
    private ProductEntity productEntity;
    @InjectMocks
    private ProductService productService;
    @Mock
    private  IProductRepositoryJPA productRepository;
    @Mock
    private  ProductMapper mapper;
    @Mock
    private  ProductValidator validator;

    //Objects used in test cases
    @BeforeEach
    public void setUp(){
        productEntity = ProductEntity.builder()
                .fantasyName("Hamburger With Chess")
                .category(ProductEntity.Category.MEATS)
                .description("Hamburger chess with potatoes")
                .build();

        productDTO = ProductDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .fantasyName("Hamburger With Chess")
                .category(ProductEntity.Category.MEATS)
                .description("Hamburger with chess extra")
                .price(2000D)
                .available(true)
                .build();
    }
    //Happy Path
    @Test
    void shouldShowProductByUuid(){
        productEntity.setUuid("efc3aa56-f8eb-46b5-96d7-7fb3eb4ae78f");
        Mockito.doNothing().when(validator).validateUuid(productEntity.getUuid());
        Mockito.when(productRepository.findByUuid(productEntity.getUuid())).thenReturn(productEntity);
        Mockito.when(mapper.EntityToDTO(productEntity)).thenReturn(productDTO);
        var response= productService.getProductByUuid(productEntity.getUuid());
        //Verify times the methods
        verify(validator, times(1)).validateUuid(productEntity.getUuid());
        verify(productRepository,times(1)).findByUuid(productEntity.getUuid());

        assertEquals(productDTO,response);
    }
    @Test
    void shouldSaveProductSuccessfully(){
        Mockito.doNothing().when(validator).validateProductDto(productDTO);
        Mockito.when(mapper.DTOToEntity(productDTO)).thenReturn(productEntity);
        Mockito.when(productRepository.existsByfantasyName(productDTO.getFantasyName())).thenReturn(Boolean.FALSE);
        Mockito.when(productRepository.save(productEntity)).thenReturn(productEntity);
        Mockito.when(mapper.EntityToDTO(productEntity)).thenReturn(productDTO);
        var response= productService.createProduct(productDTO);
        //Verify times the methods
        Mockito.verify(validator, times(1)).validateProductDto(productDTO);
        Mockito.verify(mapper, times(1)).DTOToEntity(productDTO);
        Mockito.verify(productRepository, times(1)).existsByfantasyName(productDTO.getFantasyName());
        Mockito.verify(productRepository, times(1)).save(productEntity);
        Mockito.verify(mapper, times(1)).EntityToDTO(productEntity);


        assertEquals(productDTO,response);
    }

    @Test
    void shouldUpdateProduct(){
        Mockito.doNothing().when(validator).validateUuid(productDTO.getUuid());
        Mockito.doNothing().when(validator).validateProductDto(productDTO);
        Mockito.doNothing().when(validator).validateProductExist(productDTO,productEntity);
        Mockito.when(mapper.EntityToDTO(productEntity)).thenReturn(productDTO);
        Mockito.doNothing().when(validator).productCompare(productDTO,productDTO);

        Mockito.when(productRepository.findByUuid( productDTO.getUuid())).thenReturn(productEntity);
        Mockito.when(productRepository.existsByfantasyName(productEntity.getFantasyName())).thenReturn(Boolean.FALSE);
        productService.updateProduct(productDTO);

        //Verify times the methods
        verify(validator, times(1)).validateUuid(productDTO.getUuid());
        verify(validator, times(1)).validateProductExist(productDTO,productEntity);
        verify(mapper, times(1)).EntityToDTO(productEntity);
        verify(validator, times(1)).productCompare(productDTO,productDTO);
        verify(productRepository, times(1)).findByUuid(productDTO.getUuid());
        verify(productRepository, times(1)).existsByfantasyName(productDTO.getFantasyName());


        assertEquals("HAMBURGER WITH CHESS", productEntity.getFantasyName());
    }
    @Test
    void shouldDeleteProductByUuid(){
        Mockito.doNothing().when(validator).validateUuid(productDTO.getUuid());
        Mockito.when(productRepository.findByUuid( productDTO.getUuid())).thenReturn(productEntity);
        productService.deleteProduct(productDTO.getUuid());

        //Verify times the methods
        verify(validator, times(1)).validateUuid(productDTO.getUuid());
        verify(productRepository, times(1)).findByUuid(productDTO.getUuid());

    }

    //test exception handling

    @Test
    void shouldProductNotFoundException(){
        Mockito.when(productRepository.findByUuid(productEntity.getUuid())).thenReturn(null);
        assertThrows(ProductNotFoundException.class,()->productService.getProductByUuid(productEntity.getUuid()));
        assertThrows(ProductNotFoundException.class,()->productService.deleteProduct(productEntity.getUuid()));

    }
    @Test
    void shouldFantasyNameExistsException(){
        Mockito.when(productRepository.existsByfantasyName(productEntity.getFantasyName())).thenReturn(Boolean.TRUE);
        Mockito.when(mapper.DTOToEntity(productDTO)).thenReturn(productEntity);
        assertThrows(FantasyNameExistsException.class,()->productService.createProduct(productDTO));
     }




}