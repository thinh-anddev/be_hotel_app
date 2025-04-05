package com.example.be_hotel.dto;

public class HotelBookingStat {
    private Long hotelId;
    private Long totalOrder;

    public HotelBookingStat(Long hotelId, Long totalOrder) {
        this.hotelId = hotelId;
        this.totalOrder = totalOrder;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Long totalOrder) {
        this.totalOrder = totalOrder;
    }
}
