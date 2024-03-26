package com.restaurant.Restaurant.exception.impl;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomErrorResponse {

    public String code;
    public LocalDateTime timeStamp;
    public String description;
    public String exception;
}
