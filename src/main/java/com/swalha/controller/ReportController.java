package com.swalha.controller;

import com.swalha.repository.RevenueRepository;
import com.swalha.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accountant/reports")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {
    
    @Autowired
    private RevenueRepository revenueRepository;
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Map<String, Object>> getFinancialSummary() {
        BigDecimal totalRevenue = revenueRepository.findAll().stream()
                .map(revenue -> revenue.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalExpense = expenseRepository.findAll().stream()
                .map(expense -> expense.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal netIncome = totalRevenue.subtract(totalExpense);
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRevenue", totalRevenue);
        summary.put("totalExpense", totalExpense);
        summary.put("netIncome", netIncome);
        summary.put("revenueCount", revenueRepository.count());
        summary.put("expenseCount", expenseRepository.count());
        
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/revenues")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Map<String, Object>> getRevenueReports() {
        Map<String, Object> report = new HashMap<>();
        report.put("revenues", revenueRepository.findAll());
        report.put("totalRevenue", revenueRepository.findAll().stream()
                .map(revenue -> revenue.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/expenses")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Map<String, Object>> getExpenseReports() {
        Map<String, Object> report = new HashMap<>();
        report.put("expenses", expenseRepository.findAll());
        report.put("totalExpense", expenseRepository.findAll().stream()
                .map(expense -> expense.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        
        return ResponseEntity.ok(report);
    }
}
