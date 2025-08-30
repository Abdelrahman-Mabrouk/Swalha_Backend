package com.swalha.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class PaymentRequest {
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;
    
    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "day is required")
    private Integer day;

    
    @NotNull(message = "Amount paid is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amountPaid;
    
    // Constructors
    public PaymentRequest() {}
    
    public PaymentRequest(Long studentId, Integer day,Integer month, Integer year, BigDecimal amountPaid) {
        this.studentId = studentId;
        this.day = day;
        this.month = month;
        this.year = year;
        this.amountPaid = amountPaid;
    }
    
    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public Integer getMonth() {
        return month;
    }
    
    public void setMonth(Integer month) {
        this.month = month;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    @Override
    public String toString() {
        return "PaymentRequest{" +
                "studentId=" + studentId +
                ", month=" + month +
                ", year=" + year +
                ", amountPaid=" + amountPaid +
                '}';
    }
    public void setday(Integer day) {
        this.day = day;
    }
    public Integer getday() {
        return day;
    }
}
