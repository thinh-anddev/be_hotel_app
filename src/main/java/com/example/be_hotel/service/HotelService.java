package com.example.be_hotel.service;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.reposity.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();
    List<Hotel> searchHotels(String query);
}
