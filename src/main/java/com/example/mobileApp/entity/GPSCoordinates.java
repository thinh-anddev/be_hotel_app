package com.example.mobileApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GPSCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double latitude;
    private Double longitude;

    @OneToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
