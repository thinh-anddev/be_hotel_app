package com.example.be_hotel.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Gọi Flask API để lấy danh sách hotel_id được gợi ý cho user.
     */
    public List<Integer> getRecommendedHotelsForUser(Long userId) {
        String url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:5000/recommend")
                .queryParam("user_id", userId)
                .toUriString();

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                List.class
        );

        List<Map<String, Object>> hotels = response.getBody();

        // Trích hotel_id từ mỗi phần tử
        return hotels.stream()
                .map(h -> (Integer) h.get("hotel_id"))
                .collect(Collectors.toList());
    }

    /**
     * Gọi Flask API để trigger train lại mô hình.
     */
    public void refreshModel() {
        String url = "http://localhost:5000/refresh_model";

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                HttpEntity.EMPTY,
                Map.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to refresh model: " + response.getBody());
        }
    }
}