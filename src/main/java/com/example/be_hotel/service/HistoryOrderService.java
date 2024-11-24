package com.example.be_hotel.service;

import com.example.be_hotel.entity.HistoryOrder;
import com.example.be_hotel.entity.Hotel;

import java.util.List;

public interface HistoryOrderService {
    HistoryOrder saveHistoryOrder(Long userId, List<Long> hotelIds, Integer numberPeople);
    void cancelOrder(Long userId, Long hotelId);
    List<Hotel> getAllHotelByUser(Long userId);
}
