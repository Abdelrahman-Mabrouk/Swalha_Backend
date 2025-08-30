package com.swalha.controller;

import com.swalha.entity.SystemSetting;
import com.swalha.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/settings")
@CrossOrigin(origins = "http://localhost:5173")
public class SystemSettingController {
    
    @Autowired
    private SystemSettingService systemSettingService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SystemSetting>> getAllSettings() {
        List<SystemSetting> settings = systemSettingService.getAllSettings();
        return ResponseEntity.ok(settings);
    }
    
    @GetMapping("/monthly-fee")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<BigDecimal> getMonthlyFee(@RequestBody int month, @RequestBody int year) {
        BigDecimal monthlyFee = systemSettingService.getMonthlyFee(month, year);
        return ResponseEntity.ok(monthlyFee);
    }

//    @PutMapping("/monthly-fee")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<SystemSetting> updateMonthlyFee(@RequestBody MonthlyFeeUpdateRequest request) {
//        try {
//            // Validate the new fee amount
//            BigDecimal newFee = new BigDecimal(request.getMonthlyFee());
//            if (newFee.compareTo(BigDecimal.ZERO) <= 0) {
//                return ResponseEntity.badRequest().build();
//            }
//
//            SystemSetting updatedSetting = systemSettingService.updateSetting(
//                request.getMonthlyFee(),
//                "Default monthly tuition fee in EGP",
//                    request.getMonth(),
//                    request.getYear()
//            );
//
//            if (updatedSetting != null) {
//                return ResponseEntity.ok(updatedSetting);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (NumberFormatException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PostMapping("/monthly-fee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemSetting> addMonthlyFee(@RequestBody MonthlyFeeUpdateRequest request) {
        try {
            // Validate the new fee amount
            BigDecimal newFee = new BigDecimal(request.getsettingValue());
            if (newFee.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().build();
            }
            System.out.println("----------------------/////---------------------------------------");
            System.out.println(request.getMonth()+" "+request.getYear());
            SystemSetting addSetting = systemSettingService.createSetting(
                    request.getsettingValue(),
                    request.getDescription(),
                    request.getMonth(),
                    request.getYear()
            );

            if (addSetting != null) {
                return ResponseEntity.ok(addSetting);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

    }

//    @PostMapping("/initialize")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<String> initializeDefaultSettings(@RequestBody int month,@RequestBody int year) {
//        systemSettingService.initializeDefaultSettings(month, year);
//        return ResponseEntity.ok("Default settings initialized successfully");
//    }
    
    // Inner class for request body
    public static class MonthlyFeeUpdateRequest {
        private String settingValue;
        private int year;
        private int month;
        private String description;

        public String getsettingValue() {
            return settingValue;
        }
        
        public void setsettingValue(String settingValue) {
            this.settingValue = settingValue;
        }

        public int getYear() {
            return year;
        }
        public void setYear(int year) {
            this.year = year;
        }
        public int getMonth() {
            return month;
        }
        public void setMonth(int month) {
            this.month = month;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }


    }
}
