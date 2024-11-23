package com.example.mobileApp.service;

import com.example.mobileApp.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> findAllUser();

    User getUser(Integer id);

    void updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
