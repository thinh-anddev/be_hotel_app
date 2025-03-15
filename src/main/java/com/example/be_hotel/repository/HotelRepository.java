package com.example.be_hotel.repository;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT r from Rating r WHERE r.hotel.id = :hotelId")
    List<Rating> findRatingsByHotelId(Long hotelId);
    @Modifying
    @Query("UPDATE Hotel h SET h.remainRooms = h.remainRooms - :roomOrder WHERE h.id = :hotelId AND h.remainRooms >= :roomOrder")
    int decreaseRemainRooms(@Param("hotelId") Long hotelId, @Param("roomOrder") Integer roomOrder);
    @Modifying
    @Query("UPDATE Hotel h SET h.remainRooms = h.remainRooms + :roomOrder WHERE h.id = :hotelId")
    int increaseRemainRooms(@Param("hotelId") Long hotelId, @Param("roomOrder") Integer roomOrder);
}
