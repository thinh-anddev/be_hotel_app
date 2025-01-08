package com.example.be_hotel.helper;

import java.util.Random;
import java.util.UUID;

public class PasswordUtils {
    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Mật khẩu dài 8 ký tự
    }
    public static String generateRandomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
