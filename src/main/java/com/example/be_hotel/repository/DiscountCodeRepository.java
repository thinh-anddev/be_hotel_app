package com.example.be_hotel.repository;

import com.example.be_hotel.entity.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
    
    Optional<DiscountCode> findByCode(String code);
    
    List<DiscountCode> findByIsActiveTrue();
    
    @Query("SELECT d FROM DiscountCode d WHERE d.isActive = true AND (d.validFrom IS NULL OR d.validFrom <= ?1) AND (d.validUntil IS NULL OR d.validUntil >= ?1)")
    List<DiscountCode> findAllValidDiscountCodes(LocalDateTime currentDateTime);
    
    @Query("SELECT d FROM DiscountCode d WHERE d.validUntil < ?1")
    List<DiscountCode> findAllExpiredDiscountCodes(LocalDateTime currentDateTime);
    
    List<DiscountCode> findByUsageLimitIsNotNullAndUsageCountGreaterThanEqual(Integer usageLimit);
}