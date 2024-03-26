package com.restaurant.Restaurant.exception.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataAlreadyExistsException extends RuntimeException{

    public DataAlreadyExistsException (String message){
        super(message);
    }
}
