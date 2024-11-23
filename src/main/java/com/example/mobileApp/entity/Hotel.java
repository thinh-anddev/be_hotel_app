package com.example.mobileApp.entity;

import com.example.mobileApp.entity.NearbyPlace;
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

    @OneToOne(mappedBy = "hotel")
    private GPSCoordinates gpsCoordinates;

    @OneToOne(mappedBy = "hotel")
    private Rate ratePerNight;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<NearbyPlace> nearbyPlaces;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Image> images;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Rating> ratings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<ReviewBreakdown> reviewsBreakdown;
}
