package com.example.be_hotel.repository;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT r from Rating r WHERE r.hotel.id = :hotelId")
    List<Rating> findRatingsByHotelId(Long hotelId);
}
