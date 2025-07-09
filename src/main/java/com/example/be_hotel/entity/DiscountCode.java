package com.example.be_hotel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String code;
    
    private String description;
    
    // Discount type: PERCENTAGE or FIXED_AMOUNT
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    
    // Discount value (percentage or fixed amount)
    private Double discountValue;
    
    // Minimum order amount to apply the discount
    private Double minimumOrderAmount;
    
    // Maximum discount amount (for percentage discounts)
    private Double maximumDiscountAmount;
    
    // Usage limit per code (null means unlimited)
    private Integer usageLimit;
    
    // Current usage count
    private Integer usageCount;
    
    // Is the discount code active
    private Boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validFrom;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validUntil;
    
    // Check if the discount code is valid (active, within date range, and not exceeded usage limit)
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        
        // Check if active
        if (isActive == null || !isActive) {
            return false;
        }
        
        // Check date range
        if (validFrom != null && now.isBefore(validFrom)) {
            return false;
        }
        
        if (validUntil != null && now.isAfter(validUntil)) {
            return false;
        }
        
        // Check usage limit
        if (usageLimit != null && usageCount != null && usageCount >= usageLimit) {
            return false;
        }
        
        return true;
    }
    
    // Calculate discount amount for a given order amount
    public Double calculateDiscountAmount(Double orderAmount) {
        if (orderAmount == null || orderAmount <= 0) {
            return 0.0;
        }
        
        // Check minimum order amount
        if (minimumOrderAmount != null && orderAmount < minimumOrderAmount) {
            return 0.0;
        }
        
        Double discountAmount = 0.0;
        
        if (discountType == DiscountType.PERCENTAGE) {
            discountAmount = orderAmount * (discountValue / 100.0);
            
            // Apply maximum discount if specified
            if (maximumDiscountAmount != null && discountAmount > maximumDiscountAmount) {
                discountAmount = maximumDiscountAmount;
            }
        } else if (discountType == DiscountType.FIXED_AMOUNT) {
            discountAmount = discountValue;
            
            // Ensure discount doesn't exceed order amount
            if (discountAmount > orderAmount) {
                discountAmount = orderAmount;
            }
        }
        
        return discountAmount;
    }
    
    // Increment usage count
    public void incrementUsageCount() {
        if (usageCount == null) {
            usageCount = 0;
        }
        usageCount++;
    }
    
    // Enum for discount types
    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT
    }
}