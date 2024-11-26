package com.example.be_hotel.service.iml;

import com.example.be_hotel.helper.Constant;
import com.example.be_hotel.entity.HistoryOrder;
import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.User;
import com.example.be_hotel.repository.HistoryOrderRepository;
import com.example.be_hotel.repository.HotelRepository;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.HistoryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryOrderImpl implements HistoryOrderService {
    @Autowired
    private HistoryOrderRepository historyOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public HistoryOrder saveHistoryOrder(Long userId, List<Long> hotelIds, Integer numberPeople) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Hotel> hotels = hotelRepository.findAllById(hotelIds);

        HistoryOrder historyOrder = new HistoryOrder();
        historyOrder.setUser(user);
        historyOrder.setHotels(hotels);
        historyOrder.setOrderDate(LocalDateTime.now());
        historyOrder.setNumberPeople(numberPeople);
        historyOrder.setStatus(Constant.HistoryOrder.PENDING);

        return historyOrderRepository.save(historyOrder);
    }

    @Override
    public void cancelOrder(Long userId, Long hotelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<HistoryOrder> historyOrders = historyOrderRepository.findByUser(user);
        for (HistoryOrder order : historyOrders) {
            Hotel targetHotel = order.getHotels()
                    .stream()
                    .filter(hotel -> hotel.getId().equals(hotelId))
                    .findFirst()
                    .orElse(null);

            if (targetHotel != null) {
                order.setStatus(Constant.HistoryOrder.CANCEL);
                historyOrderRepository.save(order);
                break;
            }
        }
    }

    @Override
    public List<Hotel> getAllHotelByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return historyOrderRepository.findHotelsByUser(user);
    }
}
