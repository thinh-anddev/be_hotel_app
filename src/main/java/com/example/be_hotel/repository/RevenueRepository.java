package com.example.be_hotel.repository;

import com.example.be_hotel.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue,Long> {
    @Query("SELECT SUM(r.revenue) FROM Revenue r WHERE r.hotelId = :hotelId AND FUNCTION('MONTH', r.date) = :month AND FUNCTION('YEAR', r.date) = :year")
    Double getTotalRevenueByHotelIdAndMonth(@Param("hotelId") Long hotelId,
                                            @Param("month") int month,
                                            @Param("year") int year);
    List<Revenue> getRevenuesByHotelId(@Param("hotelId") Long hotelId);
}
