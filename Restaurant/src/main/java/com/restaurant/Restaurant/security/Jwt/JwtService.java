package com.restaurant.Restaurant.security.Jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    UserDetails parseToken(String token);
}
