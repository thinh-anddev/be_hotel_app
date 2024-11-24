package com.example.be_hotel.controller;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.service.HistoryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history-orders")
public class HistoryOrderController {
    @Autowired
    private HistoryOrderService historyOrderService;

    @PostMapping("/save")
    public ResponseEntity<String> saveHistoryOrder(@RequestParam Long userId, @RequestParam Integer numberPeople, @RequestBody List<Long> hotelIds) {
        try {
            historyOrderService.saveHistoryOrder(userId, hotelIds, numberPeople);
            return new ResponseEntity<>("History order saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving history order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam Long userId, @RequestParam Long hotelId) {
        try {
            historyOrderService.cancelOrder(userId, hotelId);
            return new ResponseEntity<>("Order canceled successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error canceling order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getHotels")
    public ResponseEntity<List<Hotel>> getAllHotelByUser(@RequestParam Long userId) {
        try {
            List<Hotel> hotels = historyOrderService.getAllHotelByUser(userId);
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
