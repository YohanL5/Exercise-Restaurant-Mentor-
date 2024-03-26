package com.restaurant.Restaurant.mapper;

import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.models.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper  {

    public  ProductDTO EntityToDTO(ProductEntity productEntity){
        return  ProductDTO
                .builder()
                .uuid(productEntity.getUuid())
                .fantasyName(productEntity.getFantasyName())
                .description(productEntity.getDescription())
                .category(productEntity.getCategory())
                .price(productEntity.getPrice())
                .available(productEntity.getAvailable())
                .build();
    }
    public ProductEntity DTOToEntity(ProductDTO productDTO){
        return ProductEntity
                .builder()
                .uuid( UUID.randomUUID().toString())
                .fantasyName(productDTO.getFantasyName().toUpperCase())
                .description(productDTO.getDescription())
                .category(productDTO.getCategory())
                .price(productDTO.getPrice())
                .available(productDTO.getAvailable())
                .build();
    }

}
