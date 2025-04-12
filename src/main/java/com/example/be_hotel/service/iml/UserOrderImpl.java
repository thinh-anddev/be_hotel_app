package com.example.be_hotel.service.iml;

import com.example.be_hotel.dto.HotelBookingStat;
import com.example.be_hotel.entity.UserOrder;
import com.example.be_hotel.repository.UserOrderRepository;
import com.example.be_hotel.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserOrderImpl implements UserOrderService {
    @Autowired
    private UserOrderRepository userOrderRepository;
    @Override
    public String saveOrder(UserOrder userOrder) {
        userOrder.setDateCreated(LocalDateTime.now());
        userOrderRepository.save(userOrder);
        return "successfully";
    }

    @Override
    public UserOrder getOrderById(Long id) {
        Optional<UserOrder> optionalUserOrder = userOrderRepository.findById(id);
        return optionalUserOrder.orElse(null);
    }

    @Override
    public List<UserOrder> getListOrderUser(Long userId) {
        return userOrderRepository.findByUserId(userId);
    }

    @Override
    public String updateStatusOrder(Long id, String status) {
        String result = "";
        Optional<UserOrder> optionalUserOrder = userOrderRepository.findById(id);
        UserOrder order = optionalUserOrder.get();
        order.setOrderStatus(status);
        userOrderRepository.save(order);
        result = "successfully";
        return result;
    }

    @Override
    public boolean checkCanCancelOrder(Long id) {
        UserOrder order = getOrderById(id);
        return order.canCancel();
    }

    @Override
    public List<HotelBookingStat> getHotelBookingStats() {
        List<Object[]> rawStats = userOrderRepository.findHotelBookingStats();

        return rawStats.stream().map(row -> {
            Long hotelId = ((Number) row[0]).longValue();
            Long totalOrders = ((Number) row[1]).longValue();
            return new HotelBookingStat(hotelId, totalOrders);
        }).collect(Collectors.toList());
    }

    @Override
    public List<HotelBookingStat> findTop10MostBookedHotel() {
        List<Object[]> rawStats=userOrderRepository.findTop10HotelBookingStats();
        return rawStats.stream().map(row->{
            Long hotelId=((Number) row[0]).longValue();
            Long totalOrders=((Number) row[1]).longValue();
            return new HotelBookingStat(hotelId,totalOrders);
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<HotelBookingStat> getMostBookedHotel() {
        return getHotelBookingStats().stream().findFirst();
    }

    @Override
    public Optional<HotelBookingStat> getLeastBookedHotel() {
        List<HotelBookingStat> stats = getHotelBookingStats();
        return stats.isEmpty() ? Optional.empty() : Optional.of(stats.get(stats.size() - 1));
    }
}
