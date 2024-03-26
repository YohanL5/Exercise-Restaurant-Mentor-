package com.restaurant.Restaurant.repository;

import com.restaurant.Restaurant.entity.OrderEntity;
import com.restaurant.Restaurant.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByUuid(String uuid);

}
