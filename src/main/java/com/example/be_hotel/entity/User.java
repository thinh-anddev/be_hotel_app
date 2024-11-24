package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private Integer age;
    private String avatar;

    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private HistoryOrder historyOrder;
}
