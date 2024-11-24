package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
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

    @JsonManagedReference
    @OneToOne(mappedBy = "hotel")
    private GPSCoordinates gpsCoordinates;

    @JsonManagedReference
    @OneToOne(mappedBy = "hotel")
    private Rate ratePerNight;

    @JsonManagedReference
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<NearbyPlace> nearbyPlaces;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Image> images;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Rating> ratings;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<ReviewBreakdown> reviewsBreakdown;
}
