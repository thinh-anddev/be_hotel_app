package com.example.mobileApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewBreakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer totalMentioned;
    private Integer positive;
    private Integer negative;
    private Integer neutral;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}

