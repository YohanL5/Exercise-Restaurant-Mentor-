








package com.restaurant.Restaurant.validator;

import com.restaurant.Restaurant.exception.impl.DataAlreadyExistsException;
import com.restaurant.Restaurant.exception.impl.InvalidOrIncompleteDataException;
import com.restaurant.Restaurant.models.dto.ClientDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ClientValidator {
    public void validateCliente(ClientDTO clientDTO) {
        if (clientDTO.getName() == null || clientDTO.getName().isEmpty()){
            throw new InvalidOrIncompleteDataException("Invalid or incomplete client data");
        }
        if(clientDTO.getDocument()==null || clientDTO.getDocument().isEmpty()){
            throw new InvalidOrIncompleteDataException("Invalid or incomplete client data");
        }
        if (clientDTO.getEmail()==null || clientDTO.getEmail().isEmpty()){
            throw new InvalidOrIncompleteDataException("Invalid or incomplete client data");
        }
        if ( clientDTO.getPhone()==null || clientDTO.getPhone().isEmpty()){
            throw new InvalidOrIncompleteDataException("Invalid or incomplete client data");
        }
        if (clientDTO.getDeliveryAddress()== null || clientDTO.getDeliveryAddress().isEmpty()){
            throw  new InvalidOrIncompleteDataException("Invalid or incomplete client data");
        }
    }
    public  void validateDocumentFormat(String document) {
        Pattern documentPattern = Pattern.compile("^(CC|CE)-\\d{5,}$");
        if(!documentPattern.matcher(document).matches()){
            throw  new InvalidOrIncompleteDataException("Invalid document format");
        }
    }
    public void clientCompare(ClientDTO clientDTO, ClientDTO clientExist) {
        if (clientDTO.getName().equalsIgnoreCase(clientExist.getName())
                && clientDTO.getDocument().equalsIgnoreCase(clientExist.getDocument())
                && clientDTO.getEmail().equalsIgnoreCase(clientExist.getEmail())
                && clientDTO.getPhone().equalsIgnoreCase(clientExist.getPhone())
                && clientDTO.getDeliveryAddress().equalsIgnoreCase(clientExist.getDeliveryAddress())) {
            throw new DataAlreadyExistsException("No hay ningún campo diferente para actualizar");
        }
    }

    public boolean clientExistByDocument(String clientExist, String clientDTO) {
        return !clientExist.equalsIgnoreCase(clientDTO);
    }
}
