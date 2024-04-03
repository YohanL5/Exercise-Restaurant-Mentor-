package com.restaurant.Restaurant.service.Users;

import com.restaurant.Restaurant.models.dto.ClientDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails loginClient(ClientDTO clientDTO);
}
