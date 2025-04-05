package com.example.be_hotel.controller;

import com.example.be_hotel.entity.Revenue;
import com.example.be_hotel.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/revenue")
public class RevenueController {
    @Autowired
    RevenueService service;
    @GetMapping("/getRevenueByMonth/{hotelId}")
    public ResponseEntity<?> getMonthlyRevenue(@PathVariable Long hotelId,
                                               @RequestParam int month,
                                               @RequestParam int year) {
        Double revenue = service.getAllRevenueByHotelInMonth(hotelId, month, year);
        if (revenue == null) revenue = 0.0;
        return ResponseEntity.ok(Map.of(
                "hotelId", hotelId,
                "month", month,
                "year", year,
                "totalRevenue", revenue
        ));
    }
}
