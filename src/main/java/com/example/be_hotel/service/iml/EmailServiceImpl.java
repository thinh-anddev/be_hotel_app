package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.User;
import com.example.be_hotel.helper.PasswordUtils;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
}
