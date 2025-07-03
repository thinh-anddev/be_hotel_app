package com.example.be_hotel.dto;

import com.example.be_hotel.entity.DiscountCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for creating and updating discount codes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeRequest {
    private String code;
    private String description;
    private DiscountCode.DiscountType discountType;
    private Double discountValue;
    private Double minimumOrderAmount;
    private Double maximumDiscountAmount;
    private Integer usageLimit;
    private Boolean isActive;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    
    /**
     * Convert DTO to entity
     */
    public DiscountCode toEntity() {
        DiscountCode discountCode = new DiscountCode();
        discountCode.setCode(this.code);
        discountCode.setDescription(this.description);
        discountCode.setDiscountType(this.discountType);
        discountCode.setDiscountValue(this.discountValue);
        discountCode.setMinimumOrderAmount(this.minimumOrderAmount);
        discountCode.setMaximumDiscountAmount(this.maximumDiscountAmount);
        discountCode.setUsageLimit(this.usageLimit);
        discountCode.setIsActive(this.isActive != null ? this.isActive : true);
        discountCode.setValidFrom(this.validFrom);
        discountCode.setValidUntil(this.validUntil);
        discountCode.setDateCreated(LocalDateTime.now());
        discountCode.setUsageCount(0);
        return discountCode;
    }
    
    /**
     * Update an existing entity with DTO values
     */
    public void updateEntity(DiscountCode discountCode) {
        if (this.code != null) discountCode.setCode(this.code);
        if (this.description != null) discountCode.setDescription(this.description);
        if (this.discountType != null) discountCode.setDiscountType(this.discountType);
        if (this.discountValue != null) discountCode.setDiscountValue(this.discountValue);
        if (this.minimumOrderAmount != null) discountCode.setMinimumOrderAmount(this.minimumOrderAmount);
        if (this.maximumDiscountAmount != null) discountCode.setMaximumDiscountAmount(this.maximumDiscountAmount);
        if (this.usageLimit != null) discountCode.setUsageLimit(this.usageLimit);
        if (this.isActive != null) discountCode.setIsActive(this.isActive);
        if (this.validFrom != null) discountCode.setValidFrom(this.validFrom);
        if (this.validUntil != null) discountCode.setValidUntil(this.validUntil);
    }
}