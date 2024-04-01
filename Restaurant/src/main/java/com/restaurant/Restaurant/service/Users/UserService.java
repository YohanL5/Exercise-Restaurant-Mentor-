package com.restaurant.Restaurant.service.Users;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements IUserService{

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loginClient(String name) {
        ClientEntity client = userRepository.findByName(name);
        if (client == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(client.getName(), client.getPassword(), new ArrayList<>());
    }
}
