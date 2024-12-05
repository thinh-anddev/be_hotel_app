package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.User;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;


    @Override
    public String registerUser(User user) {
        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            return "Username already exists";
        }
        user.setDateCreated(LocalDateTime.now());
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String loginUser(String username, String password) {
        Optional<User> existingUser = userRepository.findByUserName(username);
        if (existingUser.isEmpty()) {
            return "User not found";
        }
        if (!password.equals(existingUser.get().getPassword())) {
            return "Wrong password";
        }
        return "Logged in successfully";
    }
}
