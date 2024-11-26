package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.HistoryRating;
import com.example.be_hotel.entity.Hotel;
import com.example.be_hotel.entity.User;
import com.example.be_hotel.repository.HistoryRatingRepository;
import com.example.be_hotel.repository.HotelRepository;
import com.example.be_hotel.repository.UserRepository;
import com.example.be_hotel.service.HistoryRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class HistoryRatingImpl implements HistoryRatingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryRatingRepository historyRatingRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public HistoryRating save(Long userId, int star, Hotel hotel) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HistoryRating historyRating = new HistoryRating();
        historyRating.setStar(star);
        historyRating.setUser(user);
        historyRating.setHotel(hotel);
        historyRating.setRatedDate(LocalDateTime.now());

        return historyRatingRepository.save(historyRating);
    }

    @Override
    public List<HistoryRating> getListHotelRatedByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return historyRatingRepository.findHistoryRatingsByUser(user);
    }
}
