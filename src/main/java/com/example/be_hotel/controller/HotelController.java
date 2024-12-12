package com.example.be_hotel.controller;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;
import com.example.be_hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String query) {
        List<Hotel> hotels = hotelService.searchHotels(query);
        if (hotels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("{hotelId}/ratings")
    public ResponseEntity<List<Rating>> getListRating(@PathVariable Long hotelId) {
        List<Rating> ratings = hotelService.getRatingsByHotelId(hotelId);
        if (ratings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ratings);
    }
}
