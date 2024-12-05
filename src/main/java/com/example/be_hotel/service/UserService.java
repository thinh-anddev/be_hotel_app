package com.example.be_hotel.service;

import com.example.be_hotel.entity.User;

public interface UserService {
    String registerUser(User user);
    String loginUser(String username, String password);
}
