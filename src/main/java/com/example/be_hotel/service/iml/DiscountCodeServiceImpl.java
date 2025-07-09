package com.example.be_hotel.service.iml;

import com.example.be_hotel.entity.DiscountCode;
import com.example.be_hotel.entity.UserOrder;
import com.example.be_hotel.repository.DiscountCodeRepository;
import com.example.be_hotel.service.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DiscountCodeServiceImpl implements DiscountCodeService {

    private final DiscountCodeRepository discountCodeRepository;

    @Autowired
    public DiscountCodeServiceImpl(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    @Override
    public DiscountCode createDiscountCode(DiscountCode discountCode) {
        // Set default values if not provided
        if (discountCode.getDateCreated() == null) {
            discountCode.setDateCreated(LocalDateTime.now());
        }
        if (discountCode.getUsageCount() == null) {
            discountCode.setUsageCount(0);
        }
        if (discountCode.getIsActive() == null) {
            discountCode.setIsActive(true);
        }

        return discountCodeRepository.save(discountCode);
    }

    @Override
    public Optional<DiscountCode> getDiscountCodeById(Long id) {
        return discountCodeRepository.findById(id);
    }

    @Override
    public Optional<DiscountCode> getDiscountCodeByCode(String code) {
        return discountCodeRepository.findByCode(code);
    }

    @Override
    public List<DiscountCode> getAllDiscountCodes() {
        return discountCodeRepository.findAll();
    }

    @Override
    public List<DiscountCode> getAllActiveDiscountCodes() {
        return discountCodeRepository.findByIsActiveTrue();
    }

    @Override
    public List<DiscountCode> getAllValidDiscountCodes() {
        return discountCodeRepository.findAllValidDiscountCodes(LocalDateTime.now());
    }

    @Override
    public DiscountCode updateDiscountCode(DiscountCode discountCode) {
        return discountCodeRepository.save(discountCode);
    }

    @Override
    public void deleteDiscountCode(Long id) {
        discountCodeRepository.deleteById(id);
    }

    @Override
    public String generateRandomDiscountCode() {
        // Generate a random alphanumeric code
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        // Generate a 10-character code
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }

    @Override
    public boolean validateDiscountCode(String code, Double orderAmount) {
        Optional<DiscountCode> optionalDiscountCode = discountCodeRepository.findByCode(code);
        
        if (optionalDiscountCode.isEmpty()) {
            return false;
        }
        
        DiscountCode discountCode = optionalDiscountCode.get();
        
        // Check if the discount code is valid
        if (!discountCode.isValid()) {
            return false;
        }
        
        // Check if the order amount meets the minimum requirement
        if (discountCode.getMinimumOrderAmount() != null &&
            orderAmount < discountCode.getMinimumOrderAmount()) {
            return false;
        }
        
        return true;
    }

    @Override
    public UserOrder applyDiscountCode(UserOrder order, String discountCodeStr) {
        if (discountCodeStr == null || discountCodeStr.isEmpty()) {
            return order;
        }
        
        Optional<DiscountCode> optionalDiscountCode = discountCodeRepository.findByCode(discountCodeStr);
        
        if (optionalDiscountCode.isEmpty() || !validateDiscountCode(discountCodeStr, order.getTotalPrice())) {
            return order;
        }
        
        DiscountCode discountCode = optionalDiscountCode.get();
        
        // Calculate the discount amount
        Double originalPrice = order.getTotalPrice();
        Double discountAmount = discountCode.calculateDiscountAmount(originalPrice);
        Double finalPrice = originalPrice - discountAmount;
        
        // Update the order with discount information
        order.setOriginalPrice(originalPrice);
        order.setDiscountAmount(discountAmount);
        order.setTotalPrice(finalPrice);
        order.setDiscountCode(discountCodeStr);
        
        // Increment the usage count of the discount code
        discountCode.incrementUsageCount();
        discountCodeRepository.save(discountCode);
        
        return order;
    }
}