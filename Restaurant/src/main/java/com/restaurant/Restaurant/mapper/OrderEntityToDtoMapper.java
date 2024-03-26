package com.restaurant.Restaurant.mapper;

import com.restaurant.Restaurant.entity.OrderEntity;
import com.restaurant.Restaurant.models.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderEntityToDtoMapper {

    public OrderDTO convert(OrderEntity orderEntity) {
        return OrderDTO.builder()
                .uuid(orderEntity.getUuid())
                .creationDateTime(orderEntity.getCreationDateTime())
                .clientDocument(orderEntity.getClientDocument())
                .productUuid(orderEntity.getProductUuid())
                .quantity(orderEntity.getQuantity())
                .extraInformation(orderEntity.getExtraInformation())
                .subTotal(orderEntity.getSubTotal())
                .tax(orderEntity.getTax())
                .grandTotal(orderEntity.getGrandTotal())
                .delivered(orderEntity.getDelivered())
                .deliveredDate(orderEntity.getDeliveredDate())
                .build();
    }


}