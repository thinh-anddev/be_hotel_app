package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.User;
import com.example.be_hotel.helper.PasswordUtils;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserRepository userRepository;

    public String sendNewPassword(String toEmail) {
        String rs = "";
        Optional<User> userOptional = userRepository.findUserByEmail(toEmail);
        if (userOptional.isPresent()) {
            String newPassword = PasswordUtils.generateRandomPassword();
            String subject = "Mật khẩu mới của bạn";
            String message = "Chúng tôi đã tạo mật khẩu mới cho tài khoản của bạn: " + newPassword +
                    "\nVui lòng đăng nhập và thay đổi mật khẩu của bạn sau khi đăng nhập.";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(toEmail);
            email.setSubject(subject);
            email.setText(message);
            emailSender.send(email);
            User user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            rs = "successfully";
        } else {
            rs = "email dont exist";
        }
        return rs;
    }

    @Override
    public Map<String, String> sendOTP(User user) {
        String otp = PasswordUtils.generateRandomOTP();
        String rs = "";
        Map<String, String> response = new HashMap<>();
        Optional<User> existingUserByUsername = userRepository.findByUserName(user.getUserName());
        Optional<User> existingUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (user.getUserName() == null || user.getUserName().length() < 6) {
            rs = "Username must be at least 6 characters long";
        } else if (user.getPassword() == null || user.getPassword().length() < 8) {
            rs = "Password must be at least 8 characters long";

        } else if (user.getEmail() == null || !user.getEmail().contains("@")) {
            rs = "Invalid email address";

        } else if (existingUserByUsername.isPresent()) {
            rs = "Username already exists";
        } else if (existingUserByEmail.isPresent()) {
            rs = "Email already exists";
        } else {
            rs = "successfully";
            String subject = "Ứng dụng booking hotel gửi mã OTP";
            String message = "Chúng tôi đã tạo OTP cho tài khoản của bạn: " + otp +
                    "\nVui lòng nhập OTP để tiến hành đăng kí.";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(user.getEmail());
            email.setSubject(subject);
            email.setText(message);
            emailSender.send(email);
        }
        response.put("otpCode", otp);
        response.put("message", rs);
        return response;
    }
}
