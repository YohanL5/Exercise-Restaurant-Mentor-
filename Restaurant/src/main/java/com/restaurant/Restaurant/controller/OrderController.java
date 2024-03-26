package com.restaurant.Restaurant.controller;

import com.restaurant.Restaurant.models.dto.OrderDTO;
import com.restaurant.Restaurant.service.orders.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    @Autowired
    OrderServiceImpl service;
    @PostMapping("/newOrders")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(service.createOrder(orderDTO) , HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{uuid}/delivered/{timestamp}")
    public ResponseEntity<?> updateOrderDelivered(@PathVariable String uuid, @PathVariable LocalDateTime timestamp){
        return new ResponseEntity<>(service.updateOrderDelivered(uuid, timestamp), HttpStatus.OK);
    }
}
