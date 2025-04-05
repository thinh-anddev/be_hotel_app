package com.example.be_hotel.service;

import com.example.be_hotel.entity.Revenue;

import java.util.List;

public interface RevenueService {
    String save(Revenue revenue);
    List<Revenue> getAllRevenueByHotel(Long hotelId);
    Double getAllRevenueByHotelInMonth(Long hotelId, int month, int year);
}
