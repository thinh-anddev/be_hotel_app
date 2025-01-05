package com.example.be_hotel.dto;

import com.example.be_hotel.entity.User;

public class GetUserResponse {
    private String message;
    private User user;

    // Constructor, getters, setters
    public GetUserResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}