package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.Rating;
import com.example.be_hotel.helper.SearchEngineHelper;
import com.example.be_hotel.repository.HotelRepository;
import com.example.be_hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public double getAvgRatingByHotelId(Long hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        Hotel hotel = hotelOptional.orElse(null);
        assert hotel != null;
        List<Rating> ratings = hotel.getRatings();
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        double totalStar = ratings.stream().mapToDouble(rating -> rating.getStars() * rating.getCount()).sum();
        int totalCount = ratings.stream().mapToInt(Rating::getCount).sum();
        return totalCount == 0 ? 0.0 : totalStar / totalCount;
    }

    @Override
    public int getTotalStar(Long hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        Hotel hotel = hotelOptional.orElse(null);
        assert hotel != null;
        List<Rating> ratings = hotel.getRatings();
        int totalStar = ratings.stream().mapToInt(Rating::getCount).sum();
        return totalStar;
    }

    @Override
    public Map<Integer, Integer> getCountStar(Long hotelId) {
        Map<Integer, Integer> starCountMap = new HashMap<>();
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        Hotel hotel = hotelOptional.orElse(null);
        assert hotel != null;
        List<Rating> ratings = hotel.getRatings();
        for (Rating rating : ratings) {
            starCountMap.put(rating.getStars(), rating.getCount());
        }
        return starCountMap;
    }
}
