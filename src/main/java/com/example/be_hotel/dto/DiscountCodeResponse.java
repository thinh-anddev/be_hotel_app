package com.example.be_hotel.dto;

import com.example.be_hotel.entity.DiscountCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for discount code responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeResponse {
    private Long id;
    private String code;
    private String description;
    private DiscountCode.DiscountType discountType;
    private Double discountValue;
    private Double minimumOrderAmount;
    private Double maximumDiscountAmount;
    private Integer usageLimit;
    private Integer usageCount;
    private Boolean isActive;
    private LocalDateTime dateCreated;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private Boolean isValid;
    
    /**
     * Create a response from an entity
     */
    public static DiscountCodeResponse fromEntity(DiscountCode discountCode) {
        DiscountCodeResponse response = new DiscountCodeResponse();
        response.setId(discountCode.getId());
        response.setCode(discountCode.getCode());
        response.setDescription(discountCode.getDescription());
        response.setDiscountType(discountCode.getDiscountType());
        response.setDiscountValue(discountCode.getDiscountValue());
        response.setMinimumOrderAmount(discountCode.getMinimumOrderAmount());
        response.setMaximumDiscountAmount(discountCode.getMaximumDiscountAmount());
        response.setUsageLimit(discountCode.getUsageLimit());
        response.setUsageCount(discountCode.getUsageCount());
        response.setIsActive(discountCode.getIsActive());
        response.setDateCreated(discountCode.getDateCreated());
        response.setValidFrom(discountCode.getValidFrom());
        response.setValidUntil(discountCode.getValidUntil());
        response.setIsValid(discountCode.isValid());
        return response;
    }
}