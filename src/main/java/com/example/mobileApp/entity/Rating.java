package com.example.mobileApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stars;
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
