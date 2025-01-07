package com.example.be_hotel.service.iml;

import com.example.be_hotel.dto.ChangePassword;
import com.example.be_hotel.dto.UpdateUserRequest;
import com.example.be_hotel.entity.User;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public String registerUser(User user) {
        if (user.getUserName() == null || user.getUserName().length() < 6) {
            return "Username must be at least 6 characters long";
        }
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            return "Password must be at least 8 characters long";
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return "Invalid email address";
        }

        Optional<User> existingUserByUsername = userRepository.findByUserName(user.getUserName());
        if (existingUserByUsername.isPresent()) {
            return "Username already exists";
        }

        Optional<User> existingUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (existingUserByEmail.isPresent()) {
            return "Email already exists";
        }

        user.setDateCreated(LocalDateTime.now());
        userRepository.save(user);
        return "User registered successfully";

    }
    @Override
    public Map<String, Object> loginUser(String username, String password) {
        Optional<User> existingUser = userRepository.findByUserName(username);
        Map<String, Object> response = new HashMap<>();

        if (existingUser.isEmpty()) {
            response.put("message", "User not found");
            return response;
        }
        if (!password.equals(existingUser.get().getPassword())) {
            response.put("message", "Wrong password");
            return response;
        }

        response.put("message", "Logged in successfully");
        response.put("userId", existingUser.get().getId()); // Assuming `getId()` fetches the user's ID
        return response;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
    @Override
    public String updateUser(Long id, UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();
        if (updateUserRequest.getAvatar() != null && !updateUserRequest.getAvatar().isEmpty()) {
            user.setAvatar(updateUserRequest.getAvatar());
        }
        if (updateUserRequest.getContact() != null && !updateUserRequest.getContact().isEmpty()) {
            user.setContact(updateUserRequest.getContact());
        }
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            user.setEmail(updateUserRequest.getEmail());
        }
        userRepository.save(user);
        return "User updated successfully";
    }

    @Override
    public String changePassword(Long id, ChangePassword changePassword) {
        String result = "";
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            result = "user not found";
        }
        User user = userOptional.get();
        if (!changePassword.getPassword().equals(user.getPassword())) {
            result = "wrong password";
        } else {
            if (changePassword.getNewPassword().length() < 8) {
                result = "password to short";
            } else if (!changePassword.getConfirmPassword().equals(changePassword.getNewPassword())){
                result =  "password not match";
            } else {
                result = "successfully";
                user.setPassword(changePassword.getNewPassword());
                userRepository.save(user);
            }
        }
        return result;
    }
}
