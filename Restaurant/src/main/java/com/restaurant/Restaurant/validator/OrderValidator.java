package com.restaurant.Restaurant.validator;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.entity.OrderEntity;
import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.exception.impl.ClientNotFoundException;
import com.restaurant.Restaurant.exception.impl.InvalidOrIncompleteDataException;
import com.restaurant.Restaurant.exception.impl.ProductNotFoundException;
import com.restaurant.Restaurant.models.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OrderValidator {

    public void verifyFields(OrderDTO orderDTO){

        if(orderDTO.getClientDocument() == null ||
                orderDTO.getProductUuid() == null ||
                orderDTO.getQuantity() == null
        ){
            throw new InvalidOrIncompleteDataException("Hacen falta campos por completar para hacer el pedido");
        }
    }

    public void verifyProductUuidExists ( ProductEntity product){

        if(product == null){
            throw new ProductNotFoundException("El producto no existe");
        }

    }

    public void verifyClientExists ( ClientEntity client ){

        if ( client == null){
            throw new ClientNotFoundException("El cliente no existe");
        }
    }

    public void uuidValidFormat (String uuid){
        Pattern pattern = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher uuidValidFormat = pattern.matcher(uuid);

        if (!uuidValidFormat.matches()) {
            throw new InvalidOrIncompleteDataException("Formato de Uuid no valido");
        }
    }

    public void verifyOrderExists (OrderEntity orderEntity){

        if( orderEntity == null){
            throw new ProductNotFoundException("La orden a actualizar no existe");
        }
    }


}
