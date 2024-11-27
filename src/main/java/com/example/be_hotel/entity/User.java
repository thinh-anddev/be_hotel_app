package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"historyOrder"})
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private Integer age;
    private String avatar;
    private LocalDateTime dateCreated;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<HistoryOrder> historyOrder;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<HistoryRating> historyRating;
}
