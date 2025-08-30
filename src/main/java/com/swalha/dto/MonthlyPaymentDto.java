package com.swalha.dto;


import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;

public class MonthlyPaymentDto {
    private Long id;
    private Long studentId;
    private Integer month;
    private Integer year;
    private BigDecimal amountPaid;
    private BigDecimal expectedAmount;
    private Boolean paymentStatus;

    public MonthlyPaymentDto() {}

    public MonthlyPaymentDto(Integer month, Integer year, BigDecimal amountPaid, BigDecimal expectedAmount, Boolean paymentStatus) {
        this.month = month;
        this.year = year;
        this.amountPaid = amountPaid;
        this.expectedAmount = expectedAmount;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
