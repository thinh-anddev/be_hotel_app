package com.example.be_hotel.helper;

import java.util.UUID;

public class PasswordUtils {
    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Mật khẩu dài 8 ký tự
    }
}
