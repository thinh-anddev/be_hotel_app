package com.example.be_hotel.controller;

import com.example.be_hotel.config.MailConfig;
import com.example.be_hotel.dto.ChangePassword;
import com.example.be_hotel.dto.GetUserResponse;
import com.example.be_hotel.dto.UpdateUserRequest;
import com.example.be_hotel.entity.User;
import com.example.be_hotel.helper.PasswordUtils;
import com.example.be_hotel.service.EmailService;
import com.example.be_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            String result = emailService.sendNewPassword(email);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address or error occurred.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String result = userService.registerUser(user);
            if (result.equals("User registered successfully")) {
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("register failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        try {
            Map<String, Object> result = userService.loginUser(username, password);
            String message = (String) result.get("message");

            if ("Logged in successfully".equals(message)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Login failed: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            GetUserResponse response = new GetUserResponse("Khong tim thay", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        GetUserResponse response = new GetUserResponse("User found", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            String result = userService.updateUser(id, updateUserRequest);
            if ("User updated successfully".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Update failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePassword changePassword) {
        try {
            String result = userService.changePassword(id, changePassword);
            if ("successfully".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

