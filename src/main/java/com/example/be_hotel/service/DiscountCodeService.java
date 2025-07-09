package com.example.be_hotel.service;

import com.example.be_hotel.entity.DiscountCode;
import com.example.be_hotel.entity.UserOrder;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing discount codes
 */
public interface DiscountCodeService {
    
    /**
     * Create a new discount code
     */
    DiscountCode createDiscountCode(DiscountCode discountCode);
    
    /**
     * Get a discount code by its ID
     */
    Optional<DiscountCode> getDiscountCodeById(Long id);
    
    /**
     * Get a discount code by its code value
     */
    Optional<DiscountCode> getDiscountCodeByCode(String code);
    
    /**
     * Get all discount codes
     */
    List<DiscountCode> getAllDiscountCodes();
    
    /**
     * Get all active discount codes
     */
    List<DiscountCode> getAllActiveDiscountCodes();
    
    /**
     * Get all valid discount codes (active and within date range)
     */
    List<DiscountCode> getAllValidDiscountCodes();
    
    /**
     * Update an existing discount code
     */
    DiscountCode updateDiscountCode(DiscountCode discountCode);
    
    /**
     * Delete a discount code by its ID
     */
    void deleteDiscountCode(Long id);
    
    /**
     * Generate a random discount code
     */
    String generateRandomDiscountCode();
    
    /**
     * Validate a discount code for a given order amount
     */
    boolean validateDiscountCode(String code, Double orderAmount);
    
    /**
     * Apply a discount code to an order
     */
    UserOrder applyDiscountCode(UserOrder order, String discountCode);
}