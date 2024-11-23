package com.example.mobileApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail;
    private String originalImage;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
