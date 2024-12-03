package com.example.be_hotel.controller;

import com.example.be_hotel.entity.User;
import com.example.be_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>("register successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("register failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            userService.loginUser(user);
            return new ResponseEntity<>("register successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("register failed", HttpStatus.BAD_REQUEST);
        }
    }
}
