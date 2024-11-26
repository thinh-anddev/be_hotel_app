package com.example.be_hotel.service;

import com.example.be_hotel.entity.HistoryRating;
import com.example.be_hotel.entity.Hotel;

import java.util.List;

public interface HistoryRatingService {
    HistoryRating save(Long userId, int star, Hotel hotel);
    List<HistoryRating> getListHotelRatedByUser(Long userId);
}
