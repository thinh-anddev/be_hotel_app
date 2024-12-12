package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;
import com.example.be_hotel.helper.SearchEngineHelper;
import com.example.be_hotel.repository.HotelRepository;
import com.example.be_hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }


    @Override
    public List<Hotel> searchHotels(String query) {
        String processQuery = SearchEngineHelper.removeDiacritics(query.trim().toLowerCase().replaceAll("\\s+", " "));

        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .filter(hotel -> {
                    boolean matchesName = SearchEngineHelper.removeDiacritics(hotel.getName()).toLowerCase().contains(processQuery);

                    boolean matchesNearbyPlaces = hotel.getNearbyPlaces().stream()
                            .anyMatch(place -> {
                                String placeName = SearchEngineHelper.
                                        removeDiacritics(place.getName());
                                return placeName != null && !placeName.trim().isEmpty() && placeName.toLowerCase().contains(processQuery);
                            });

                    return matchesName || matchesNearbyPlaces;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        return hotelOptional.orElse(null);
    }

    @Override
    public List<Rating> getRatingsByHotelId(Long hotelId) {
        return hotelRepository.findRatingsByHotelId(hotelId);
    }
}
