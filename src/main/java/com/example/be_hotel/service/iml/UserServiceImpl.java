package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.User;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public String registerUser(User user) {
        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            return "Username already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String loginUser(User user) {
        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser.isEmpty()) {
            return "User not found";
        }
        if (!passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return "Wrong password";
        }
        return "Logged in successfully";
    }
}
