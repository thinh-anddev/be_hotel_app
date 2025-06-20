package com.example.be_hotel.controller;

import com.example.be_hotel.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommend")
    public ResponseEntity<List<Integer>> recommend(@RequestParam Long userId) {
        return ResponseEntity.ok(recommendationService.getRecommendedHotelsForUser(userId));
    }

    @PostMapping("/refresh-model")
    public ResponseEntity<?> refreshModel() {
        recommendationService.refreshModel();
        return ResponseEntity.ok(Map.of("message", "Model refreshed"));
    }
}
