package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;
    private String link;
    private String checkInTime;
    private String checkOutTime;
    private String hotelClass;
    private Integer extractedHotelClass;
    private Double overallRating;
    private Integer reviews;
    private Double locationRating;
    private String propertyToken;
    private String serpapiPropertyDetailsLink;
    private Integer remainRooms;

    // One-to-one: GPS coordinates (có khóa ngoại từ GPS trỏ về Hotel)
    @JsonManagedReference
    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private GPSCoordinates gpsCoordinates;

    // One-to-one: Rate per night (có khóa ngoại từ Rate trỏ về Hotel)
    @JsonManagedReference
    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Rate ratePerNight;

    // ElementCollection: amenities (không cần cascade)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    @ToString.Exclude
    private List<String> amenities;

    @JsonManagedReference
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<NearbyPlace> nearbyPlaces;

    @JsonManagedReference
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Image> images;

    @JsonManagedReference
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Rating> ratings;
    @JsonManagedReference
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ReviewBreakdown> reviewsBreakdown;
}