package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class UserOrder {
    @Id
    @GeneratedValue
    private Long id;
    private Long hotelId;
    private Long userId;
    private String orderCode;
    private String orderName;
    private String orderContact;
    private String orderEmail;
    private String orderStatus;
    private Integer numberPeople;
    private Double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;
}
