package com.restaurant.Restaurant.validator;

import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.exception.impl.DataAlreadyExistsException;
import com.restaurant.Restaurant.exception.impl.FantasyNameExistsException;
import com.restaurant.Restaurant.exception.impl.InvalidOrIncompleteDataException;
import com.restaurant.Restaurant.exception.impl.ProductNotFoundException;
import com.restaurant.Restaurant.models.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ProductValidator {

    public void validateProductDto(ProductDTO productDTO){
        if (productDTO.getFantasyName()==null || productDTO.getFantasyName().isEmpty()){
            throw new InvalidOrIncompleteDataException("Fantasy name is empty");
        }
        if ( productDTO.getCategory()==null ){
            throw new InvalidOrIncompleteDataException("Category is empty or Invalid");
        }
        if (productDTO.getDescription()==null || productDTO.getDescription().isEmpty()){
            throw new InvalidOrIncompleteDataException("Description is empty");
        }
        if ( productDTO.getPrice()==null || productDTO.getPrice()<=0D){
            throw new InvalidOrIncompleteDataException("Price is empty or invalid");
        }
        if (productDTO.getAvailable()==null ){
            throw new InvalidOrIncompleteDataException("Available is empty or invalid");
        }

    }
    public void validateProductExist(ProductDTO productDTO,ProductEntity productExist){
        if (productExist==null){
            throw  new ProductNotFoundException("Product con uuid " + productDTO.getUuid() + " no existe");
        }
    }
    public void validateUuid(String uuid){
        Pattern UuidPattern= Pattern.compile("^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$");
        if (!UuidPattern.matcher(uuid).matches()){
            throw new InvalidOrIncompleteDataException("Uuid no es valido");
        }
    }
    public void productCompare(ProductDTO productDTO, ProductDTO productExist){
        if (productDTO.getFantasyName().equalsIgnoreCase(productExist.getFantasyName())
            && productDTO.getCategory().equals(productExist.getCategory())
                && productDTO.getDescription().equalsIgnoreCase(productExist.getDescription())
                    && productDTO.getPrice().equals(productExist.getPrice())
                        && productDTO.getAvailable().equals(productExist.getAvailable())){
            throw new DataAlreadyExistsException("No hay ningun campo diferente para actualizar");
        }
    }
    public void productExistFantasyName(String productExist,String productDto,boolean exist){
        if (exist && !productExist.equalsIgnoreCase(productDto)){
            throw new FantasyNameExistsException("Producto con nombre fantasia ya existe");
        }
    }

}
