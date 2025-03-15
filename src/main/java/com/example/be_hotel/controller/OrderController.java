package com.example.be_hotel.controller;

import com.example.be_hotel.dto.ListOrderResponse;
import com.example.be_hotel.entity.UserOrder;
import com.example.be_hotel.service.HotelService;
import com.example.be_hotel.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    UserOrderService service;
    @Autowired
    HotelService hotelService;

    @PostMapping("/saveOrder")
    public ResponseEntity<String> saveOrder(@RequestBody UserOrder order) {
        String response = service.saveOrder(order);
        boolean b = hotelService.decreaseRemainRoom(order.getHotelId(), order.getRooms());
        if (response.equals("successfully")) {
            if(b){
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getOrder/{id}")
    public ResponseEntity<UserOrder> getOrderById(@PathVariable Long id) {
        UserOrder order = service.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @GetMapping("/getListOrder/{userId}")
    public ResponseEntity<ListOrderResponse> getListOrderByUser(@PathVariable Long userId) {
        List<UserOrder> orders = service.getListOrderUser(userId);
        ListOrderResponse response = new ListOrderResponse();
        if (!orders.isEmpty()) {
            response.setMessage("successfully");
            response.setOrderList(orders);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.setMessage("empty");
            response.setOrderList(orders);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/cancelOrder/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
        boolean canCancel=service.checkCanCancelOrder(orderId);
        if(canCancel){
            String response = service.updateStatusOrder(orderId, "CANCEL");
            UserOrder order =service.getOrderById(orderId);
            if(order==null){
                return ResponseEntity.badRequest().body("failed");
            }else{
                hotelService.increaseRemainRoom(orderId, order.getRooms());
                return ResponseEntity.ok("successfully");
            }
        }else{
            return ResponseEntity.badRequest().body("cannt cancel");
        }
    }
    @PutMapping("/updateOrderStatus/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        String response = service.updateStatusOrder(orderId, status);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
