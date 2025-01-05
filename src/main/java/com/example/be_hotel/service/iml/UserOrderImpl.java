package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.UserOrder;
import com.example.be_hotel.repository.UserOrderRepository;
import com.example.be_hotel.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
}
