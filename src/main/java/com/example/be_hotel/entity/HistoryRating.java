package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class HistoryRating {

    @Id
    @GeneratedValue
    private Long id;

    private int star;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Hotel hotel;

    private LocalDateTime ratedDate;
}
