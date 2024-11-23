package com.example.be_hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lowest;
    private Integer extractedLowest;
    private String beforeTaxesFees;
    private Integer extractedBeforeTaxesFees;

    @OneToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
