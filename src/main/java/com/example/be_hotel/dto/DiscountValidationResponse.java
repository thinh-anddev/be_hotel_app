package com.example.be_hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for discount code validation response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountValidationResponse {
    private boolean valid;
    private String message;
    private Double originalPrice;
    private Double discountAmount;
    private Double finalPrice;
    
    /**
     * Constructor for invalid discount codes
     */
    public DiscountValidationResponse(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
        this.originalPrice = null;
        this.discountAmount = null;
        this.finalPrice = null;
    }
    
    /**
     * Static factory method for creating a valid discount response
     */
    public static DiscountValidationResponse createValidResponse(Double originalPrice, Double discountAmount, Double finalPrice) {
        return new DiscountValidationResponse(
            true,
            "Discount code is valid",
            originalPrice,
            discountAmount,
            finalPrice
        );
    }
    
    /**
     * Static factory method for creating an invalid discount response
     */
    public static DiscountValidationResponse createInvalidResponse(String message) {
        return new DiscountValidationResponse(false, message);
    }
}