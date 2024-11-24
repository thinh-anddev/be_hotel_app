package com.example.be_hotel.reposity;

import com.example.be_hotel.entity.HistoryOrder;
import com.example.be_hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryOrderRepository extends JpaRepository<HistoryOrder, Long> {
    List<HistoryOrder> findByUser(User user);
}
