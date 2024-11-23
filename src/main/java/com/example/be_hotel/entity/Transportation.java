package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String duration;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "nearby_place_id")
    private NearbyPlace nearbyPlace;
}

