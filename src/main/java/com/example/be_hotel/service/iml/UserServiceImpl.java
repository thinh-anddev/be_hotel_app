package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.User;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public  class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid user id"));
        return user;
    }

    @Override
    public void updateUser(Long id, User user) {
        userRepository
                .findById(id)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid user id"+ id)));
        user.setId(id);

        userRepository.save(user);

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid user id"+ id)));
        userRepository.delete(user);

    }
}
