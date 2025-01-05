package com.example.be_hotel.dto;

import com.example.be_hotel.entity.UserOrder;

import java.util.List;

public class ListOrderResponse {
    private String message;
    private List<UserOrder> orderList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<UserOrder> orderList) {
        this.orderList = orderList;
    }
}
