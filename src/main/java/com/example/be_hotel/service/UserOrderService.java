package com.example.be_hotel.service;

import com.example.be_hotel.entity.UserOrder;

import java.util.List;

public interface UserOrderService {
    String saveOrder(UserOrder userOrder);
    UserOrder getOrderById(Long id);
    List<UserOrder> getListOrderUser(Long userId);
    String updateStatusOrder(Long id, String status);
}
