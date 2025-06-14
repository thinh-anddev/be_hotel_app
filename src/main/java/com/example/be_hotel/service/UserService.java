package com.example.be_hotel.service;

import com.example.be_hotel.dto.ChangePassword;
import com.example.be_hotel.dto.UpdateUserForAdmin;
import com.example.be_hotel.dto.UpdateUserRequest;
import com.example.be_hotel.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    String registerUser(User user);
    Map<String, Object> loginUser(String username, String password);
    User getUserById(Long id);
    String updateUser(Long id, UpdateUserRequest updateUserRequest);
    String changePassword(Long id, ChangePassword changePassword);
    List<User> getAllUsers();
    String updateUserForAdmin(Long id, UpdateUserForAdmin user);
    String deleteUser(Long id);
}
