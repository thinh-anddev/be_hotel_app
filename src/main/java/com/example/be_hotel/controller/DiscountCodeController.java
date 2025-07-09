package com.example.be_hotel.controller;

import com.example.be_hotel.dto.DiscountCodeRequest;
import com.example.be_hotel.dto.DiscountCodeResponse;
import com.example.be_hotel.entity.DiscountCode;
import com.example.be_hotel.service.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discount-codes")
public class DiscountCodeController {

    @Autowired
    private DiscountCodeService discountCodeService;

    @PostMapping
    public ResponseEntity<DiscountCodeResponse> createDiscountCode(@RequestBody DiscountCodeRequest request) {
        DiscountCode discountCode = request.toEntity();
        DiscountCode createdDiscountCode = discountCodeService.createDiscountCode(discountCode);
        DiscountCodeResponse response = DiscountCodeResponse.fromEntity(createdDiscountCode);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DiscountCodeResponse>> getAllDiscountCodes() {
        List<DiscountCode> discountCodes = discountCodeService.getAllDiscountCodes();
        List<DiscountCodeResponse> responses = discountCodes.stream()
                .map(DiscountCodeResponse::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<DiscountCodeResponse>> getAllActiveDiscountCodes() {
        List<DiscountCode> discountCodes = discountCodeService.getAllActiveDiscountCodes();
        List<DiscountCodeResponse> responses = discountCodes.stream()
                .map(DiscountCodeResponse::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/valid")
    public ResponseEntity<List<DiscountCodeResponse>> getAllValidDiscountCodes() {
        List<DiscountCode> discountCodes = discountCodeService.getAllValidDiscountCodes();
        List<DiscountCodeResponse> responses = discountCodes.stream()
                .map(DiscountCodeResponse::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCodeResponse> getDiscountCodeById(@PathVariable Long id) {
        Optional<DiscountCode> discountCode = discountCodeService.getDiscountCodeById(id);
        return discountCode.map(code -> {
                    DiscountCodeResponse response = DiscountCodeResponse.fromEntity(code);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<DiscountCodeResponse> getDiscountCodeByCode(@PathVariable String code) {
        Optional<DiscountCode> discountCode = discountCodeService.getDiscountCodeByCode(code);
        return discountCode.map(value -> {
                    DiscountCodeResponse response = DiscountCodeResponse.fromEntity(value);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCodeResponse> updateDiscountCode(@PathVariable Long id, @RequestBody DiscountCodeRequest request) {
        Optional<DiscountCode> optionalDiscountCode = discountCodeService.getDiscountCodeById(id);
        if (optionalDiscountCode.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DiscountCode existingDiscountCode = optionalDiscountCode.get();
        request.updateEntity(existingDiscountCode);
        DiscountCode updatedDiscountCode = discountCodeService.updateDiscountCode(existingDiscountCode);
        DiscountCodeResponse response = DiscountCodeResponse.fromEntity(updatedDiscountCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscountCode(@PathVariable Long id) {
        Optional<DiscountCode> existingDiscountCode = discountCodeService.getDiscountCodeById(id);
        if (existingDiscountCode.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        discountCodeService.deleteDiscountCode(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateRandomDiscountCode() {
        String code = discountCodeService.generateRandomDiscountCode();
        return new ResponseEntity<>(code, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateDiscountCode(@RequestParam String code, @RequestParam Double orderAmount) {
        boolean isValid = discountCodeService.validateDiscountCode(code, orderAmount);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
