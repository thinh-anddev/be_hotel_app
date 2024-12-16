package com.example.be_hotel.service;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;

import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();
    List<Hotel> searchHotels(String query);
    Hotel getHotelById(Long hotelId);
    List<Rating> getRatingsByHotelId(Long hotelId);
    double getAvgRatingByHotelId(Long hotelId);
}
