package com.example.be_hotel.service;

import com.example.be_hotel.entity.User;

import java.util.Map;

public interface EmailService {
    String sendNewPassword(String toEmail);
    Map<String, String> sendOTP(User user);
}
