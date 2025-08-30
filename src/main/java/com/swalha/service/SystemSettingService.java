package com.swalha.service;

import com.swalha.entity.SystemSetting;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SystemSettingService {
    

    String getSettingValue(int month, int year);
    SystemSetting getSettingByMonthAndYear(int month, int year) ;


    BigDecimal getMonthlyFee(int month, int year);
    
//    SystemSetting updateSetting( String value, String description, LocalDate effectiveDate);
    
    SystemSetting createSetting( String value, String description,int month, int year);
    
    List<SystemSetting> getAllSettings();
    
//    void initializeDefaultSettings(int month, int year);

//    Optional<LocalDate> getMonthlyFeeByDate(LocalDate effectiveDate);
}
