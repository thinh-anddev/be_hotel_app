package com.example.be_hotel.repository;

import com.example.be_hotel.entity.HistoryOrder;
import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryOrderRepository extends JpaRepository<HistoryOrder, Long> {
    @Query("SELECT h FROM HistoryOrder ho JOIN ho.hotels h WHERE ho.user = :user")
    List<Hotel> findHotelsByUser(@Param("user") User user);

    List<HistoryOrder> findByUser(User user);
}
