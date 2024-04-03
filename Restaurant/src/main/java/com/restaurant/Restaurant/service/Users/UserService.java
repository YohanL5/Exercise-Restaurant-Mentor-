package com.restaurant.Restaurant.service.Users;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.models.dto.ClientDTO;
import com.restaurant.Restaurant.repository.IUserRepository;
import com.restaurant.Restaurant.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements IUserService{

    private final IUserRepository userRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserService(IUserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public UserDetails loginClient(ClientDTO clientDTO) {
        ClientEntity client = userRepository.findByName(clientDTO.name);
        if (client == null) {
            throw new UsernameNotFoundException("User not found");
        }
        userValidator.UserValidatorPassword(client,clientDTO);
        return new User(client.getName(), client.getPassword(), new ArrayList<>());
    }
}
