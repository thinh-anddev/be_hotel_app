package com.example.be_hotel.repository;

import com.example.be_hotel.entity.HistoryRating;
import com.example.be_hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRatingRepository extends JpaRepository<HistoryRating, Long> {
    List<HistoryRating> findHistoryRatingsByUser(User user);
}
