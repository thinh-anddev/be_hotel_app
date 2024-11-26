package com.example.be_hotel.controller;

import com.example.be_hotel.entity.HistoryRating;
import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.service.HistoryRatingService;
import com.example.be_hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history-rating")
public class HistoryRatingController {
    @Autowired
    private HistoryRatingService historyRatingService;
    @Autowired
    private HotelService hotelService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRating(@RequestParam Long userId, @RequestParam int star, @RequestParam Long hotelId) {
        try {
            Hotel hotel = hotelService.getHotelById(hotelId);
            historyRatingService.save(userId, star, hotel);
            return new ResponseEntity<>("History rating saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving history rating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getHistoryRate")
    public ResponseEntity<List<HistoryRating>> getHistoryRated(@RequestParam Long userId) {
        try {
            List<HistoryRating> listHistoryRating = historyRatingService.getListHotelRatedByUser(userId);
            return new ResponseEntity<>(listHistoryRating, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
