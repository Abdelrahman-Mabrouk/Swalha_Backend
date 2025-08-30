package com.swalha.controller;

import com.swalha.entity.Revenue;
import com.swalha.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accountant/revenues")
@CrossOrigin(origins = "http://localhost:5173")
public class RevenueController {
    
    @Autowired
    private RevenueRepository revenueRepository;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<List<Revenue>> getAllRevenues() {
        List<Revenue> revenues = revenueRepository.findAll();
        return ResponseEntity.ok(revenues);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Revenue> getRevenueById(@PathVariable Long id) {
        Optional<Revenue> revenue = revenueRepository.findById(id);
        return revenue.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<?> createRevenue(@RequestBody Revenue revenue) {
        try {
            System.out.println("Received revenue: " + revenue);
            Revenue savedRevenue = revenueRepository.save(revenue);
            System.out.println("Saved revenue: " + savedRevenue);
            return ResponseEntity.ok(savedRevenue);
        } catch (Exception e) {
            System.out.println("Error creating revenue: " + e.getMessage());
            e.printStackTrace();
            
            // Check if it's a constraint violation
            if (e.getMessage().contains("constraint") || e.getMessage().contains("check")) {
                return ResponseEntity.badRequest()
                    .body("Invalid category value. Allowed values are: Tuition_Fees, DONATION, BOOKS, DOCUMENTS, VODAFONE_CASH, OTHER");
            }
            
            return ResponseEntity.badRequest().body("Error creating revenue: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Revenue> updateRevenue(@PathVariable Long id, @RequestBody Revenue revenue) {
        Optional<Revenue> existingRevenue = revenueRepository.findById(id);
        if (existingRevenue.isPresent()) {
            Revenue updatedRevenue = existingRevenue.get();
            updatedRevenue.setName(revenue.getName());
            updatedRevenue.setAmount(revenue.getAmount());
            updatedRevenue.setMonth(revenue.getMonth());
            updatedRevenue.setCategory(revenue.getCategory());
            updatedRevenue.setNotes(revenue.getNotes());
            
            Revenue savedRevenue = revenueRepository.save(updatedRevenue);
            return ResponseEntity.ok(savedRevenue);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Void> deleteRevenue(@PathVariable Long id) {
        if (revenueRepository.existsById(id)) {
            revenueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
