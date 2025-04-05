package com.example.be_hotel.service;

import com.example.be_hotel.dto.HotelBookingStat;
import com.example.be_hotel.entity.UserOrder;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {
    String saveOrder(UserOrder userOrder);
    UserOrder getOrderById(Long id);
    List<UserOrder> getListOrderUser(Long userId);
    String updateStatusOrder(Long id, String status);
    boolean checkCanCancelOrder(Long id);
    List<HotelBookingStat> getHotelBookingStats();
    Optional<HotelBookingStat> getMostBookedHotel();
    Optional<HotelBookingStat> getLeastBookedHotel();
}
