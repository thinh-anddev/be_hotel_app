package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.Revenue;
import com.example.be_hotel.repository.RevenueRepository;
import com.example.be_hotel.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    RevenueRepository repository;
    @Override
    public String save(Revenue revenue) {
        repository.save(revenue);
        return "successfull";
    }

    @Override
    public List<Revenue> getAllRevenueByHotel(Long hotelId) {
        return repository.getRevenuesByHotelId(hotelId);
    }

    @Override
    public Double getAllRevenueByHotelInMonth(Long hotelId, int month, int year) {
        return repository.getTotalRevenueByHotelIdAndMonth(hotelId,month,year);
    }
}
