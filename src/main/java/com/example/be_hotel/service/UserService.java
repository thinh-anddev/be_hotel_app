package com.example.be_hotel.service;

import com.example.be_hotel.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> findAllUser();

    User getUser(Integer id);

    void updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
