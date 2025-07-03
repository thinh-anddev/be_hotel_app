package com.example.be_hotel.controller;

import com.example.be_hotel.dto.DiscountValidationResponse;
import com.example.be_hotel.dto.HotelBookingStat;
import com.example.be_hotel.dto.ListOrderResponse;
import com.example.be_hotel.entity.Revenue;
import com.example.be_hotel.entity.UserOrder;
import com.example.be_hotel.service.DiscountCodeService;
import com.example.be_hotel.service.HotelService;
import com.example.be_hotel.service.RevenueService;
import com.example.be_hotel.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    UserOrderService service;
    @Autowired
    HotelService hotelService;
    @Autowired
    RevenueService revenueService;
    @Autowired
    DiscountCodeService discountCodeService;

    @PostMapping("/saveOrder")
    public ResponseEntity<String> saveOrder(@RequestBody UserOrder order) {
        // Apply discount code if provided
        if (order.getDiscountCode() != null && !order.getDiscountCode().isEmpty()) {
            // Validate and apply the discount code
            if (discountCodeService.validateDiscountCode(order.getDiscountCode(), order.getTotalPrice())) {
                order = discountCodeService.applyDiscountCode(order, order.getDiscountCode());
            } else {
                return new ResponseEntity<>("Invalid discount code", HttpStatus.BAD_REQUEST);
            }
        }

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

    @GetMapping("/validateDiscountCode")
    public ResponseEntity<DiscountValidationResponse> validateDiscountCode(@RequestParam String code, @RequestParam Double orderAmount) {
        boolean isValid = discountCodeService.validateDiscountCode(code, orderAmount);
        if (isValid) {
            // Get the discount code details
            return discountCodeService.getDiscountCodeByCode(code)
                    .map(discountCode -> {
                        Double discountAmount = discountCode.calculateDiscountAmount(orderAmount);
                        Double finalPrice = orderAmount - discountAmount;

                        // Return discount information using the DTO
                        DiscountValidationResponse response = DiscountValidationResponse.createValidResponse(
                            orderAmount,
                            discountAmount,
                            finalPrice
                        );

                        return ResponseEntity.ok(response);
                    })
                    .orElse(ResponseEntity.ok(DiscountValidationResponse.createInvalidResponse("Discount code not found")));
        } else {
            return ResponseEntity.ok(DiscountValidationResponse.createInvalidResponse("Invalid discount code"));
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
    @PutMapping("/successfullPayment/{orderId}")
    public ResponseEntity<String> successfullPayment(@PathVariable Long orderId) {
        String response = service.updateStatusOrder(orderId, "PAID");
        UserOrder order = service.getOrderById(orderId);
        Revenue revenue = new Revenue();
        revenue.setHotelId(order.getHotelId());
        revenue.setRevenue(order.getTotalPrice());
        revenue.setDate(LocalDateTime.now());
        revenueService.save(revenue);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/getAllHotelStats")
    public ResponseEntity<List<HotelBookingStat>> getAllHotelStats() {
        List<HotelBookingStat> stats = service.getHotelBookingStats();
        if (stats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stats);
    } @GetMapping("/getTop10HotelStats")
    public ResponseEntity<List<HotelBookingStat>> getTop10HotelStats() {
        List<HotelBookingStat> stats = service.findTop10MostBookedHotel();
        if (stats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/most-booked")
    public ResponseEntity<HotelBookingStat> getMostBookedHotel() {
        return service.getMostBookedHotel()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
    @GetMapping("/least-booked")
    public ResponseEntity<HotelBookingStat> getLeastBookedHotel() {
        return service.getLeastBookedHotel()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

}
