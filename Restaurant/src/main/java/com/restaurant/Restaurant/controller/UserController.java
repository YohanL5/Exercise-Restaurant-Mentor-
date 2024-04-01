package com.restaurant.Restaurant.controller;

import com.restaurant.Restaurant.models.dto.ClientDTO;
import com.restaurant.Restaurant.security.Jwt.JwtServiceImpl;
import com.restaurant.Restaurant.service.Users.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtServiceImpl jwtService;

    public UserController(UserService userService, JwtServiceImpl jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    //Security Jwt Methods the login and getClient by name
    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody ClientDTO clientDTO){
        UserDetails clientDetails= userService.loginClient(clientDTO.getDocument());
        String token= jwtService.generateToken(clientDetails);
        return ResponseEntity.ok(token);
    }
}
