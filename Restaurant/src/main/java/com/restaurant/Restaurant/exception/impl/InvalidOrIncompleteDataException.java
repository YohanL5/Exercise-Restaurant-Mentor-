package com.restaurant.Restaurant.exception.impl;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOrIncompleteDataException extends  RuntimeException{

    public InvalidOrIncompleteDataException (String message){
        super(message);
    }
}
