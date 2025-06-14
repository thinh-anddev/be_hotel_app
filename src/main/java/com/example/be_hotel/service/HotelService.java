package com.example.be_hotel.service;

import com.example.be_hotel.dto.HotelDTO;
import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<Hotel> getAllHotels();
    List<Hotel> searchHotels(String query);
    Hotel getHotelById(Long hotelId);
    List<Rating> getRatingsByHotelId(Long hotelId);
    double getAvgRatingByHotelId(Long hotelId);
    int getTotalStar(Long hotelId);
    Map<Integer, Integer> getCountStar(Long hotelId);
    boolean decreaseRemainRoom(Long hotelId, Integer roomOrder);
    boolean increaseRemainRoom(Long hotelId, Integer roomOrder);
    Hotel addHotel(HotelDTO hotelDTO);
    boolean deleteHotel(Long hotelId);
}
