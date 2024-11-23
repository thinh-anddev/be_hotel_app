package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
