package com.restaurant.Restaurant.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    public String document;
    public String name;
    public String email;
    public String phone;
    public String deliveryAddress;

}