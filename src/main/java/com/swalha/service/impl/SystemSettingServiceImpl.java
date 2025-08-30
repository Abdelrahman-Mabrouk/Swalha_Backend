package com.swalha.service.impl;

import com.swalha.entity.SystemSetting;
import com.swalha.repository.SystemSettingRepository;
import com.swalha.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SystemSettingServiceImpl implements SystemSettingService {
    
    @Autowired
    private SystemSettingRepository systemSettingRepository;
    
    private static final String DEFAULT_MONTHLY_FEE = "70.00";
    
    @Override
    public SystemSetting getSettingByMonthAndYear(int month, int year) {
        Optional<SystemSetting> setting = systemSettingRepository.findByMonthAndYear(month, year);
        return setting.orElse(null);
    }
    
    @Override
    public String getSettingValue(int month, int year) {
        SystemSetting setting = getSettingByMonthAndYear(month, year);
        return setting != null ? setting.getSettingValue() : null;
    }
    
    @Override
    public BigDecimal getMonthlyFee(int month, int year) {
        String feeValue = getSettingValue(month,year);
        if (feeValue == null) {
            // Initialize default if not exists
//            initializeDefaultSettings(month, year);
            feeValue = DEFAULT_MONTHLY_FEE;
        }
        
        try {
            return new BigDecimal(feeValue);
        } catch (NumberFormatException e) {
            return new BigDecimal(DEFAULT_MONTHLY_FEE);
        }
    }
    
//    @Override
//    public SystemSetting updateSetting(String value, String description, LocalDate effectiveDate) {
//        SystemSetting setting = systemSettingRepository.findByEffectiveDate(effectiveDate).orElseThrow(
//                () -> new RuntimeException("System setting not found")
//        );
//            setting.setSettingValue(value);
//                setting.setDescription(description);
//            if (effectiveDate != null) {
//                setting.setMonth(montff);
//            }
//            return (systemSettingRepository.save(setting));
//        }

    
    @Override
    public SystemSetting createSetting(String value, String description,int month, int year) {
        SystemSetting setting = new SystemSetting(value, description, month , year);
        Optional<SystemSetting> testSetting = systemSettingRepository.findByMonthAndYear(month,year);
        if (testSetting.isPresent()) {
            new RuntimeException("System setting already exists");
        }
        return systemSettingRepository.save(setting);
    }
    
    @Override
    public List<SystemSetting> getAllSettings() {
        return systemSettingRepository.findAll();
    }
    
//    @Override
//    public void initializeDefaultSettings(int month, int year) {
//        // Initialize monthly fee if it doesn't exist
//        if (getSettingByMonthAndYear(month,year) == null) {
//            createSetting(
//                DEFAULT_MONTHLY_FEE,
//                "Default monthly tuition fee in EGP",
//                    LocalDate.now().getMonthValue(),
//                    LocalDate.now().getYear()
//            );
//        }
//    }


}
