package com.restaurant.Restaurant.validator;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.models.dto.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public void UserValidatorPassword(ClientEntity clientEntity, ClientDTO clientDTO){
        if (!clientDTO.getPassword().equalsIgnoreCase(clientEntity.getPassword())){
            throw new IllegalArgumentException("Password Incorrect");
        }
    }
}
