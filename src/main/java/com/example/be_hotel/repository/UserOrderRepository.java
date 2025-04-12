package com.example.be_hotel.repository;

import com.example.be_hotel.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    @Override
    Optional<UserOrder> findById(Long id);
    List<UserOrder> findByUserId(Long userId);
    @Query(value = """
    SELECT hotel_id, COUNT(*) as total_orders
    FROM user_order
    WHERE order_status = 'PAID'
    GROUP BY hotel_id
    ORDER BY total_orders DESC
""", nativeQuery = true)
    List<Object[]> findHotelBookingStats();
    @Query(value = """
    SELECT hotel_id, COUNT(*) as total_orders
    FROM user_order
    WHERE order_status = 'PAID'
    GROUP BY hotel_id
    ORDER BY total_orders DESC
    LIMIT 10
""", nativeQuery = true)
    List<Object[]> findTop10HotelBookingStats();

}
