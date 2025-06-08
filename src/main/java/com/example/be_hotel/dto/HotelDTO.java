package com.example.be_hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelDTO {
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
    private List<String> amenities;
    private List<ImageDTO> images;
}
