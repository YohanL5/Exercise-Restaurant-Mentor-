package com.restaurant.Restaurant.service.orders;

import com.restaurant.Restaurant.models.dto.OrderDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOrderService {


    OrderDTO createOrder(OrderDTO orderDTO );

    OrderDTO updateOrderDelivered(String  uuid, LocalDateTime timeStamp);

}