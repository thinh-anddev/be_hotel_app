package com.example.be_hotel.repository;

import com.example.be_hotel.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    @Override
    Optional<UserOrder> findById(Long id);
    List<UserOrder> findByUserId(Long userId);
}
