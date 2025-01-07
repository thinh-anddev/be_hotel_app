package com.example.be_hotel.repository;

import com.example.be_hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    @Override
    Optional<User> findById(Long aLong);
    Optional<User> findUserByEmail(String email);
}
