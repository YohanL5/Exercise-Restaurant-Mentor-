package com.restaurant.Restaurant.service.Users;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails loginClient(String document);
}
